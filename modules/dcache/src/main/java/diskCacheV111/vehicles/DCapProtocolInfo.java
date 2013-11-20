package diskCacheV111.vehicles;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;

import dmg.cells.nucleus.CellPath;

public class DCapProtocolInfo implements IpProtocolInfo {

    private final String _name;
    private final int _minor;
    private final int _major;
    @Deprecated // Can be removed in 2.7
    private final String[] _hosts;
    @Deprecated // Can be removed in 2.7
    private final int _port;
    private InetSocketAddress _addr;
    private long _transferTime;
    private long _bytesTransferred;
    private int _sessionId;
    private boolean _writeAllowed;
    private boolean _isPassive;
    private CellPath _door;

    private static final long serialVersionUID = 7432555710192378884L;

    public DCapProtocolInfo(String protocol, int major, int minor,
            InetSocketAddress addr) {
        _name = protocol;
        _minor = minor;
        _major = major;
        _addr = addr;
        _hosts = new String[] { addr.getHostString() };
        _port = addr.getPort();
    }

    public int getSessionId() {
        return _sessionId;
    }

    public void setSessionId(int sessionId) {
        _sessionId = sessionId;
    }

    //
    // the ProtocolInfo interface
    //
    @Override
    public String getProtocol() {
        return _name;
    }

    @Override
    public int getMinorVersion() {
        return _minor;
    }

    @Override
    public int getMajorVersion() {
        return _major;
    }

    @Override
    public String getVersionString() {
        return _name + "-" + _major + "." + _minor;
    }

    //
    // and the private stuff
    //
    public void setBytesTransferred(long bytesTransferred) {
        _bytesTransferred = bytesTransferred;
    }

    public void setTransferTime(long transferTime) {
        _transferTime = transferTime;
    }

    public long getTransferTime() {
        return _transferTime;
    }

    public long getBytesTransferred() {
        return _bytesTransferred;
    }

    //
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getVersionString()).append(',');
        sb.append(_addr.getAddress().getHostAddress());
        sb.append(":").append(_addr.getPort());

        return sb.toString();
    }

    //
    // io mode
    //
    public boolean isWriteAllowed() {
        return _writeAllowed;
    }

    public void setAllowWrite(boolean allow) {
        _writeAllowed = allow;
    }

    public boolean isPassive() {
        return _isPassive;
    }

    public void isPassive(boolean passive) {
        _isPassive = passive;
    }

    public CellPath door() {
        return _door;
    }

    public void door(CellPath door) {
        _door = door;
    }

    @Override
    public InetSocketAddress getSocketAddress() {
        return _addr;
    }

    // For compatibility with pre 2.6
    private void readObject(ObjectInputStream stream)
            throws IOException, ClassNotFoundException
    {
        stream.defaultReadObject();
        if (_addr == null && _hosts.length > 0) {
            _addr = new InetSocketAddress(_hosts[0], _port);
        }
    }
}
