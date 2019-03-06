import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class Gui implements MouseListener {
    
    private static final Midi midi = new Midi();
    private final JButton startBtn = new JButton(START);
    private final JLabel bpmLabel = new JLabel(Integer.toString(midi.getBpm()));
    private final JLabel instrumentLabel = new JLabel(SIDE_STICK);
    private static final int MAX_BPM = 660;
    private static final int MIN_BPM = 10;
    private static final String START = "Start";
    private static final String STOP = "Stop";
    private static final String SIDE_STICK = "Side Stick";
    private static final String SNARE_DRUM = "Snare Drum";
    private static final String BASS_DRUM = "Bass Drum";
    
    Gui() {
        JFrame frame = new JFrame("Metronome");
        frame.setSize(300, 400);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        startBtn.setBounds(110, 130, 80, 60);
        startBtn.addMouseListener(this);
        startBtn.setFont(startBtn.getFont().deriveFont(18f));
        
        bpmLabel.setBounds(130, 10, 40, 40);
        bpmLabel.setFont(bpmLabel.getFont().deriveFont(Font.BOLD, 20f));
        instrumentLabel.setBounds(90, 50, 200, 40);
        instrumentLabel.setFont(instrumentLabel.getFont().deriveFont(Font.BOLD, 20f));
        
        JLabel desc = new JLabel();
        desc.setBounds(70, 100, 400, 400);
        desc.setFont(desc.getFont().deriveFont(16f));
        desc.setText
                ("<html>SPACE = Start/stop" +
                        "<br>UP/DOWN = BPM +- 10" +
                        "<br>RIGHT/LEFT = BPM +- 1" +
                        "<br>1 = Side Stick" +
                        "<br>2 = Snare Drum" +
                        "<br>3 = Bass Drum");
        
        KeyboardFocusManager kManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        kManager.addKeyEventDispatcher(new KeyDispatcher());
        
        frame.getContentPane().add(startBtn);
        frame.getContentPane().add(instrumentLabel);
        frame.getContentPane().add(bpmLabel);
        frame.getContentPane().add(desc);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if (!midi.getSequencer().isRunning()) {
            midi.start();
            startBtn.setText(STOP);
        } else {
            midi.stop();
            startBtn.setText(START);
        }
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
    
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
    
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
    
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
    
    }
    
    private class KeyDispatcher implements KeyEventDispatcher {
        
        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            int bpm = midi.getBpm();
            if (e.getID() == KeyEvent.KEY_PRESSED) {
                switch (e.getKeyCode()) {
                case KeyEvent.VK_SPACE:
                    if (midi.getSequencer().isRunning()) {
                        midi.stop();
                        startBtn.setText(START);
                        break;
                    } else {
                        midi.start();
                        startBtn.setText(STOP);
                        break;
                    }
                case KeyEvent.VK_1:
                    midi.changeInstrument(Midi.SIDE_STICK);
                    instrumentLabel.setText(SIDE_STICK);
                    break;
                case KeyEvent.VK_2:
                    midi.changeInstrument(Midi.SNARE_DRUM);
                    instrumentLabel.setText(SNARE_DRUM);
                    break;
                case KeyEvent.VK_3:
                    midi.changeInstrument(Midi.BASS_DRUM);
                    instrumentLabel.setText(BASS_DRUM);
                    break;
                case KeyEvent.VK_UP:
                    bpm += 10;
                    break;
                case KeyEvent.VK_DOWN:
                    bpm -= 10;
                    break;
                case KeyEvent.VK_RIGHT:
                    bpm += 1;
                    break;
                case KeyEvent.VK_LEFT:
                    bpm -= 1;
                    break;
                default:
                    break;
                }
                if (bpm <= MAX_BPM && bpm >= MIN_BPM) {
                    midi.setBpm(bpm);
                    bpmLabel.setText(Integer.toString(bpm));
                }
            }
            return false;
        }
    }
}
