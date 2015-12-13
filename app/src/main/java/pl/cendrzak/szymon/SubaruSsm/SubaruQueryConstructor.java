package pl.cendrzak.szymon.SubaruSsm;

/**
 * Created by Szymon on 2014-12-11.
 */
public class SubaruQueryConstructor {

    public byte[] getQueryForParameter(SubaruParameter parameter)
    {
        switch(parameter) {
            case ENGINE_SPEED:
                return getEngineSpeedQuery();
            case ENGINE_LOAD:
                return getEngineLoadQuery();
            case ENGINE_TEMP:
                return getEngineTempQuery();
            case END_CONNECTION:
                return getEndConnectionQuery();
            default:
                throw new IllegalArgumentException();
        }
    }

    public byte getMsbForParameter(SubaruParameter parameter)
    {
        return getQueryForParameter(parameter)[2];
    }

    public byte getLsbForParameter(SubaruParameter parameter)
    {
        return getQueryForParameter(parameter)[3];
    }

    private byte[] getEngineSpeedQuery()
    {
        return new byte[] { 0x02, 0x78, 0x00, 0x09, 0x17};
    }

    private byte[] getEngineLoadQuery()
    {
        return new byte[] { 0x02, 0x78, 0x00, 0x0D, 0x17};
    }

    private byte[] getEngineTempQuery()
    {
        return new byte[] { 0x02, 0x78, 0x00, 0x0A, 0x17};
    }

    private byte[] getEndConnectionQuery() { return new byte[] { 0x02, 0x12, 0x00, 0x00, 0x17}; }
}
