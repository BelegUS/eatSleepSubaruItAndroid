package pl.cendrzak.szymon.SubaruSsm;

import java.io.ByteArrayOutputStream;

public abstract class SubaruQuery {
    public ByteArrayOutputStream getQueryBytes()
    {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bytes.write(0x02);
        try {
            bytes.write(getBytes().toByteArray());
        } catch (Exception e) {}    //Gotta' catch 'em all, eh?
        bytes.write(0x17);
        return bytes;
    }

    protected abstract ByteArrayOutputStream getBytes();
}
