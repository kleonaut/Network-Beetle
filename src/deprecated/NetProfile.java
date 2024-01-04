package deprecated;

// immutable
public class NetProfile
{
    public static final NetProfile OFFLINE = new NetProfile("disconnected");

    private final String name;

    public NetProfile(String name) { this.name = name; }

    public String name() { return name; }
}
