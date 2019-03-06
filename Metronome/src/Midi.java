import javax.sound.midi.*;

class Midi {
    
    private Sequencer sequencer;
    private Sequence sequence;
    private int bpm = 60;
    private Track track;
    final static int BASS_DRUM = 35;
    final static int SIDE_STICK = 37;
    final static int SNARE_DRUM = 38;
    
    Midi() {
        openSequencer();
        createSequence();
        editTrack(SIDE_STICK);
        loadSequence();
        sequencer.setTempoInBPM(bpm);
        createLoop();
    }
    
    int getBpm() {
        return bpm;
    }
    
    void setBpm(int bpm) {
        this.bpm = bpm;
        sequencer.setTempoInBPM(bpm);
    }
    
    Sequencer getSequencer() {
        return sequencer;
    }
    
    private void openSequencer() {
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }
    
    private void createSequence() {
        try {
            sequence = new Sequence(Sequence.PPQ, 1);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }
    
    private void editTrack(int instrument) {
        if (track == null) track = sequence.createTrack();
        try {
            ShortMessage msg = new ShortMessage(ShortMessage.NOTE_ON, 9, instrument, 80);
            MidiEvent evt = new MidiEvent(msg, 0);
            track.add(evt);
            msg = new ShortMessage(ShortMessage.NOTE_OFF, 9, instrument, 0);
            evt = new MidiEvent(msg, 1);
            track.add(evt);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }
    
    private void loadSequence() {
        try {
            sequencer.setSequence(sequence);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }
    
    private void createLoop() {
        sequencer.setLoopStartPoint(0);
        sequencer.setLoopEndPoint(-1);
        sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
    }
    
    void start() { sequencer.start(); }
    
    void stop() { sequencer.stop(); }
    
    private void reload(int instrument) {
        track = null;
        createSequence();
        editTrack(instrument);
        loadSequence();
    }
    
    void changeInstrument(int instrument) {
        if (sequencer.isRunning()) {
            sequencer.stop();
            reload(instrument);
            sequencer.start();
        } else reload(instrument);
    }
}
