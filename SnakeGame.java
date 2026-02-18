import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

public class SnakeGame extends JFrame {
    public static final int BOARD_SIZE = 12;
    public static final int TILE_SIZE = 50;
    public static final int INITIAL_SNAKE_SIZE = 5;
    public static final int TARGET_SIZE_FOR_NEXT_LEVEL = 15;
    
    private GamePanel gamePanel;
    private int level = 1;
    private boolean gameRunning = false;
    private LinkedList<PlayerScore> ranking;
    
    public SnakeGame() {
        ranking = new LinkedList<>();
        initializeGame();
    }
    
    private void initializeGame() {
        setTitle("Snake Game - Nivel: " + level);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        gamePanel = new GamePanel(this);
        add(gamePanel);
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        
        startGame();
    }
    
    private void startGame() {
        gameRunning = true;
        gamePanel.startGame();
    }
    
    public void gameOver() {
        if (!gameRunning) return;
        
        gameRunning = false;
        gamePanel.stopGame();
        
        // Agregar al ranking
        String playerName = JOptionPane.showInputDialog(this, 
            "Game Over! Nivel alcanzado: " + level + 
            "\nTamaño final: " + gamePanel.getSnakeSize() +
            "\nIngresa tu nombre:");
        
        if (playerName == null || playerName.trim().isEmpty()) {
            playerName = "Jugador";
        }
        
        ranking.add(new PlayerScore(playerName, level, gamePanel.getSnakeSize()));
        Collections.sort(ranking);
        
        // Mantener solo top 5
        while (ranking.size() > 5) {
            ranking.removeLast();
        }
        
        showRanking();
        
        // Preguntar si quiere jugar de nuevo
        int option = JOptionPane.showConfirmDialog(this, 
            "¿Quieres jugar de nuevo?", "Game Over", 
            JOptionPane.YES_NO_OPTION);
        
        if (option == JOptionPane.YES_OPTION) {
            level = 1;
            gamePanel.resetGame();
            setTitle("Snake Game - Nivel: " + level);
            startGame();
        } else {
            System.exit(0);
        }
    }
    
    public void nextLevel() {
        level++;
        setTitle("Snake Game - Nivel: " + level);
        gamePanel.nextLevel(level);
    }
    
    public int getLevel() {
        return level;
    }
    
    private void showRanking() {
        StringBuilder rankingText = new StringBuilder("TOP 5 JUGADORES:\n\n");
        
        for (int i = 0; i < ranking.size(); i++) {
            PlayerScore ps = ranking.get(i);
            rankingText.append((i + 1)).append(". ")
                      .append(ps.getName()).append(" - Nivel: ")
                      .append(ps.getLevel()).append(" - Tamaño: ")
                      .append(ps.getSize()).append("\n");
        }
        
        if (ranking.isEmpty()) {
            rankingText.append("No hay puntuaciones aún");
        }
        
        JOptionPane.showMessageDialog(this, rankingText.toString(), "Ranking", 
                                    JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SnakeGame();
        });
    }
}

// Clase para el panel del juego
class GamePanel extends JPanel implements ActionListener, KeyListener {
    private SnakeGame parent;
    private LinkedList<Point> snake;
    private List<Point> foods;
    private List<Point> traps;
    private int direction = KeyEvent.VK_RIGHT;
    private int nextDirection = KeyEvent.VK_RIGHT;
    private javax.swing.Timer gameTimer;
    private int initialDelay = 150;
    private int currentDelay;
    private Color backgroundColor;
    private boolean growing = false;
    private boolean trapActivated = false;
    
    // Optimizaciones de rendimiento
    private BufferedImage gameBuffer;
    private boolean backgroundDirty = true;
    private boolean foodsDirty = true;
    private boolean trapsDirty = true;
    private Point oldTail = null;
    private Point newHead = null;
    private List<Point> segmentsToRemove = new ArrayList<>(); // NUEVO: segmentos a limpiar
    
    public GamePanel(SnakeGame parent) {
        this.parent = parent;
        setPreferredSize(new Dimension(SnakeGame.BOARD_SIZE * SnakeGame.TILE_SIZE, 
                                     SnakeGame.BOARD_SIZE * SnakeGame.TILE_SIZE));
        setBackground(Color.BLACK);
        backgroundColor = new Color(20, 20, 20);
        setFocusable(true);
        addKeyListener(this);
        
        // Inicializar listas
        foods = new ArrayList<>();
        traps = new ArrayList<>();
        
        // Inicializar buffer principal
        int width = SnakeGame.BOARD_SIZE * SnakeGame.TILE_SIZE;
        int height = SnakeGame.BOARD_SIZE * SnakeGame.TILE_SIZE;
        gameBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        resetGame();
    }
    
    public void startGame() {
        currentDelay = initialDelay;
        gameTimer = new javax.swing.Timer(currentDelay, this);
        gameTimer.start();
    }
    
    public void stopGame() {
        if (gameTimer != null) {
            gameTimer.stop();
        }
    }
    
    public void resetGame() {
        stopGame();
        
        snake = new LinkedList<>();
        segmentsToRemove.clear();
        
        // Inicializar snake en el centro
        int startX = SnakeGame.BOARD_SIZE / 2;
        int startY = SnakeGame.BOARD_SIZE / 2;
        
        for (int i = 0; i < SnakeGame.INITIAL_SNAKE_SIZE; i++) {
            snake.add(new Point(startX - i, startY));
        }
        
        // Generar comidas y trampas según el nivel
        spawnFoods();
        spawnTraps();
        
        direction = KeyEvent.VK_RIGHT;
        nextDirection = KeyEvent.VK_RIGHT;
        currentDelay = initialDelay;
        growing = false;
        trapActivated = false;
        
        // Marcar todo para redibujar
        backgroundDirty = true;
        foodsDirty = true;
        trapsDirty = true;
        
        repaint();
    }
    
    public void nextLevel(int newLevel) {
        // Oscurecer el fondo
        int darken = Math.min(100, newLevel * 2);
        int r = Math.max(20, 20 + darken);
        int g = Math.max(20, 20 + darken);
        int b = Math.max(20, 20 + darken);
        backgroundColor = new Color(r, g, b);
        
        // Aumentar velocidad
        currentDelay = Math.max(50, initialDelay - (newLevel * 10));
        if (gameTimer != null) {
            gameTimer.setDelay(currentDelay);
        }
        
        // Reset snake size
        snake.clear();
        segmentsToRemove.clear();
        
        int startX = SnakeGame.BOARD_SIZE / 2;
        int startY = SnakeGame.BOARD_SIZE / 2;
        
        for (int i = 0; i < SnakeGame.INITIAL_SNAKE_SIZE; i++) {
            snake.add(new Point(startX - i, startY));
        }
        
        // Generar nuevas comidas y trampas según el nuevo nivel
        spawnFoods();
        spawnTraps();
        
        direction = KeyEvent.VK_RIGHT;
        nextDirection = KeyEvent.VK_RIGHT;
        growing = false;
        trapActivated = false;
        
        // Marcar todo para redibujar
        backgroundDirty = true;
        foodsDirty = true;
        trapsDirty = true;
        
        repaint();
    }
    
    private void spawnFoods() {
        foods.clear();
        int numberOfFoods = parent.getLevel();
        
        Random rand = new Random();
        for (int i = 0; i < numberOfFoods; i++) {
            Point newFood;
            int attempts = 0;
            do {
                newFood = new Point(rand.nextInt(SnakeGame.BOARD_SIZE), rand.nextInt(SnakeGame.BOARD_SIZE));
                attempts++;
                if (attempts > 50) break;
            } while (snake.contains(newFood) || foods.contains(newFood) || traps.contains(newFood));
            
            if (attempts <= 50) {
                foods.add(newFood);
            }
        }
        foodsDirty = true;
    }
    
    private void spawnTraps() {
        traps.clear();
        int numberOfTraps = parent.getLevel();
        
        Random rand = new Random();
        for (int i = 0; i < numberOfTraps; i++) {
            Point newTrap;
            int attempts = 0;
            do {
                newTrap = new Point(rand.nextInt(SnakeGame.BOARD_SIZE), rand.nextInt(SnakeGame.BOARD_SIZE));
                attempts++;
                if (attempts > 50) break;
            } while (snake.contains(newTrap) || traps.contains(newTrap) || foods.contains(newTrap));
            
            if (attempts <= 50) {
                traps.add(newTrap);
            }
        }
        trapsDirty = true;
    }
    
    private void move() {
        direction = nextDirection;
        Point head = new Point(snake.getFirst());
        
        switch (direction) {
            case KeyEvent.VK_UP:
                head.y = (head.y - 1 + SnakeGame.BOARD_SIZE) % SnakeGame.BOARD_SIZE;
                break;
            case KeyEvent.VK_DOWN:
                head.y = (head.y + 1) % SnakeGame.BOARD_SIZE;
                break;
            case KeyEvent.VK_LEFT:
                head.x = (head.x - 1 + SnakeGame.BOARD_SIZE) % SnakeGame.BOARD_SIZE;
                break;
            case KeyEvent.VK_RIGHT:
                head.x = (head.x + 1) % SnakeGame.BOARD_SIZE;
                break;
        }
        
        // Verificar colisión consigo mismo (excluyendo la cabeza)
        for (int i = 1; i < snake.size(); i++) {
            if (snake.get(i).equals(head)) {
                parent.gameOver();
                return;
            }
        }
        
        // Guardar posición de la cola para limpiar después
        oldTail = snake.isEmpty() ? null : new Point(snake.getLast());
        
        // Agregar nueva cabeza
        snake.addFirst(head);
        newHead = new Point(head);
        
        // Verificar comidas
        boolean foodEaten = false;
        Iterator<Point> foodIterator = foods.iterator();
        while (foodIterator.hasNext()) {
            Point food = foodIterator.next();
            if (head.equals(food)) {
                foodIterator.remove();
                growing = true;
                foodEaten = true;
                break;
            }
        }
        
        // Si se comió una comida, generar una nueva
        if (foodEaten) {
            spawnNewFood();
        }
        
        // Verificar trampas
        boolean trapActivatedThisMove = false;
        Iterator<Point> trapIterator = traps.iterator();
        while (trapIterator.hasNext()) {
            Point trap = trapIterator.next();
            if (head.equals(trap)) {
                trapIterator.remove();
                trapActivated = true;
                trapActivatedThisMove = true;
                break;
            }
        }
        
        // Si se activó una trampa, generar una nueva
        if (trapActivatedThisMove) {
            spawnNewTrap();
        }
        
        // Lógica de movimiento - CORREGIDA
        if (trapActivated) {
            if (snake.size() > 2) {
                // Guardar los segmentos que se van a remover para limpiarlos
                Point removedSegment1 = snake.removeLast(); // Remover cola normal
                Point removedSegment2 = snake.removeLast(); // Remover segmento adicional por la trampa
                
                // Agregar a la lista de segmentos a limpiar
                segmentsToRemove.add(removedSegment1);
                segmentsToRemove.add(removedSegment2);
            } else {
                parent.gameOver();
                return;
            }
            trapActivated = false;
        } else if (!growing) {
            Point removedSegment = snake.removeLast(); // Movimiento normal
            segmentsToRemove.add(removedSegment);
        } else {
            growing = false; // Comida - no remover cola
        }
        
        // Verificar si pasa de nivel
        if (snake.size() >= SnakeGame.TARGET_SIZE_FOR_NEXT_LEVEL) {
            parent.nextLevel();
        }
        
        // Dibujar solo los cambios
        partialRepaint();
    }
    
    private void spawnNewFood() {
        Random rand = new Random();
        Point newFood;
        int attempts = 0;
        do {
            newFood = new Point(rand.nextInt(SnakeGame.BOARD_SIZE), rand.nextInt(SnakeGame.BOARD_SIZE));
            attempts++;
            if (attempts > 50) break;
        } while (snake.contains(newFood) || foods.contains(newFood) || traps.contains(newFood));
        
        if (attempts <= 50) {
            foods.add(newFood);
            foodsDirty = true;
        }
    }
    
    private void spawnNewTrap() {
        Random rand = new Random();
        Point newTrap;
        int attempts = 0;
        do {
            newTrap = new Point(rand.nextInt(SnakeGame.BOARD_SIZE), rand.nextInt(SnakeGame.BOARD_SIZE));
            attempts++;
            if (attempts > 50) break;
        } while (snake.contains(newTrap) || traps.contains(newTrap) || foods.contains(newTrap));
        
        if (attempts <= 50) {
            traps.add(newTrap);
            trapsDirty = true;
        }
    }
    
    private void drawBackground(Graphics g) {
        g.setColor(backgroundColor);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        // Dibujar grid sutil para mejor visibilidad
        g.setColor(new Color(50, 50, 50));
        for (int i = 0; i <= SnakeGame.BOARD_SIZE; i++) {
            g.drawLine(i * SnakeGame.TILE_SIZE, 0, i * SnakeGame.TILE_SIZE, getHeight());
            g.drawLine(0, i * SnakeGame.TILE_SIZE, getWidth(), i * SnakeGame.TILE_SIZE);
        }
    }
    
    private void drawFoods(Graphics g) {
        for (Point food : foods) {
            g.setColor(new Color(255, 215, 0));
            g.fillOval(food.x * SnakeGame.TILE_SIZE, food.y * SnakeGame.TILE_SIZE, 
                      SnakeGame.TILE_SIZE, SnakeGame.TILE_SIZE);
        }
    }
    
    private void drawTraps(Graphics g) {
        for (Point trap : traps) {
            g.setColor(new Color(220, 20, 60));
            g.fillRect(trap.x * SnakeGame.TILE_SIZE, trap.y * SnakeGame.TILE_SIZE, 
                      SnakeGame.TILE_SIZE, SnakeGame.TILE_SIZE);
            g.setColor(Color.WHITE);
            //g.drawString("",trap.x * SnakeGame.TILE_SIZE + SnakeGame.TILE_SIZE/2 - 3, trap.y * SnakeGame.TILE_SIZE + SnakeGame.TILE_SIZE/2 + 3);
        }
    }
    
    private void drawSnakeSegment(Graphics g, Point segment, boolean isHead) {
        if (isHead) {
            g.setColor(new Color(34, 139, 34));
        } else {
            g.setColor(new Color(34, 139, 34));
        }
        g.fillRect(segment.x * SnakeGame.TILE_SIZE, 
                  segment.y * SnakeGame.TILE_SIZE, 
                  SnakeGame.TILE_SIZE, SnakeGame.TILE_SIZE);
        g.setColor(Color.GRAY);
        g.drawRect(segment.x * SnakeGame.TILE_SIZE, 
                  segment.y * SnakeGame.TILE_SIZE, 
                  SnakeGame.TILE_SIZE, SnakeGame.TILE_SIZE);
    }
    
    //private void drawUI(Graphics g) {g.setColor(Color.WHITE);g.drawString("Nivel: " + parent.getLevel(), 10, 15);g.drawString("Tamaño: " + getSnakeSize(), 10, 30);g.drawString("Objetivo: " + SnakeGame.TARGET_SIZE_FOR_NEXT_LEVEL, 10, 45);g.drawString("Comidas: " + foods.size() + "/" + parent.getLevel(), 10, 60);g.drawString("Trampas: " + traps.size() + "/" + parent.getLevel(), 10, 75);}
    
    private void cleanSegment(Graphics g, Point segment) {
        g.setColor(backgroundColor);
        g.fillRect(segment.x * SnakeGame.TILE_SIZE, 
                  segment.y * SnakeGame.TILE_SIZE, 
                  SnakeGame.TILE_SIZE, SnakeGame.TILE_SIZE);
        // Redibujar grid en esa área
        g.setColor(new Color(50, 50, 50));
        g.drawRect(segment.x * SnakeGame.TILE_SIZE, 
                  segment.y * SnakeGame.TILE_SIZE, 
                  SnakeGame.TILE_SIZE, SnakeGame.TILE_SIZE);
    }
    
    private void partialRepaint() {
        Graphics g = gameBuffer.getGraphics();
        
        // Solo redibujar fondo si es necesario (al cambiar nivel o reset)
        if (backgroundDirty) {
            drawBackground(g);
            backgroundDirty = false;
            // Cuando el fondo cambia, hay que redibujar todo
            drawFoods(g);
            drawTraps(g);
            foodsDirty = false;
            trapsDirty = false;
            
            // Redibujar snake completo
            if (snake != null && !snake.isEmpty()) {
                for (int i = 0; i < snake.size(); i++) {
                    drawSnakeSegment(g, snake.get(i), i == 0);
                }
            }
        } else {
            // PRIMERO: Limpiar todos los segmentos removidos (incluyendo trampas)
            for (Point segment : segmentsToRemove) {
                cleanSegment(g, segment);
            }
            segmentsToRemove.clear(); // Limpiar la lista después de procesar
            
            // SEGUNDO: Limpiar posición anterior de la cola (movimiento normal)
            if (oldTail != null && !growing && !trapActivated) {
                cleanSegment(g, oldTail);
            }
            
            // TERCERO: Redibujar comidas si es necesario
            if (foodsDirty) {
                drawFoods(g);
                foodsDirty = false;
            }
            
            // CUARTO: Redibujar trampas si es necesario
            if (trapsDirty) {
                drawTraps(g);
                trapsDirty = false;
            }
            
            // QUINTO: Dibujar nueva cabeza
            if (newHead != null) {
                drawSnakeSegment(g, newHead, true);
            }
        }
        
        // Dibujar UI (siempre se redibuja)
        //drawUI(g);
        
        g.dispose();
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Dibujar el buffer completo en pantalla
        g.drawImage(gameBuffer, 0, 0, this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        move();
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        
        if ((key == KeyEvent.VK_LEFT) && (direction != KeyEvent.VK_RIGHT)) {
            nextDirection = KeyEvent.VK_LEFT;
        } else if ((key == KeyEvent.VK_RIGHT) && (direction != KeyEvent.VK_LEFT)) {
            nextDirection = KeyEvent.VK_RIGHT;
        } else if ((key == KeyEvent.VK_UP) && (direction != KeyEvent.VK_DOWN)) {
            nextDirection = KeyEvent.VK_UP;
        } else if ((key == KeyEvent.VK_DOWN) && (direction != KeyEvent.VK_UP)) {
            nextDirection = KeyEvent.VK_DOWN;
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}
    
    @Override
    public void keyReleased(KeyEvent e) {}
    
    public int getSnakeSize() {
        return snake != null ? snake.size() : 0;
    }
}

// Clase para el ranking
class PlayerScore implements Comparable<PlayerScore> {
    private String name;
    private int level;
    private int size;
    
    public PlayerScore(String name, int level, int size) {
        this.name = name;
        this.level = level;
        this.size = size;
    }
    
    public String getName() { return name; }
    public int getLevel() { return level; }
    public int getSize() { return size; }
    
    @Override
    public int compareTo(PlayerScore other) {
        if (this.level != other.level) {
            return other.level - this.level; // Mayor nivel primero
        }
        return other.size - this.size; // Mayor tamaño si mismo nivel
    }
}