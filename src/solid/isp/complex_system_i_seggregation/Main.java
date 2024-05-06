package solid.isp.complex_system_i_seggregation;

interface MultimediaDevice {
    void playAudio();
    void playVideo();
    void recordAudio();
    void takePhoto();
}


// This is wrong and violates ISP : (look at the solution folder)
class Camera implements MultimediaDevice {
    public void playAudio() {
        // Not applicable
    }

    public void playVideo() {
        // Not applicable
    }

    public void recordAudio() {
        // Not applicable
    }

    public void takePhoto() {
        System.out.println("Taking a photo");
    }
}

class SmartPhone implements MultimediaDevice {
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
