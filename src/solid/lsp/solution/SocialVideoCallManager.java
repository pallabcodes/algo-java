package solid.lsp.solution;


// By default, any method declared in a Java interface is abstract, meaning it does not have an implementation and must be implemented by any class that implements the interface. However, starting with Java 8, interfaces can also contain default methods and static methods which do have implementations. Thus, while methods in interfaces are abstract by default, default methods and static methods are exceptions to this rule.
public interface SocialVideoCallManager {
    public void groupVideoCall(String... users);
}
