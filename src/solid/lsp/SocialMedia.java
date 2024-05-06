package solid.lsp;

public abstract class SocialMedia {

    // @support Whatsapp, Facebook, Instagram
    public abstract void chatWithFriend();

    // @support Facebook, Instagram
    public abstract void publishPost(Object post);

    // @support Whatsapp, Facebook, Instagram
    public abstract void sendPhotosAndVideos();

    // @support Whatsapp, Facebook
    public abstract void groupVideoCall(String... users);
}
