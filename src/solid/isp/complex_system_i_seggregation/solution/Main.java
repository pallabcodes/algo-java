package solid.isp.complex_system_i_seggregation.solution;

interface AudioPlayer {
    void playAudio();
}

interface VideoPlayer {
    void playVideo();
}

interface AudioRecorder {
    void recordAudio();
}

interface PhotoTaker {
    void takePhoto();
}

class Camera implements PhotoTaker {
    public void takePhoto() {
        System.out.println("Taking a photo");
    }
}

class SmartPhone implements AudioPlayer, VideoPlayer, AudioRecorder, PhotoTaker {
    public void playAudio() {
        System.out.println("Playing audio");
    }

    public void playVideo() {
        System.out.println("Playing video");
    }

    public void recordAudio() {
        System.out.println("Recording audio");
    }

    public void takePhoto() {
        System.out.println("Taking a photo");
    }
}
