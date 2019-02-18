package cn.luowq.api.internal.apachecommons.io.input;

import java.io.InputStream;

/**
 * @Author: rowan
 * @Date: 2019/2/18 16:48
 * @Description:
 */
public class ClosedInputStream extends InputStream {
    public static final ClosedInputStream CLOSED_INPUT_STREAM = new ClosedInputStream();

    public ClosedInputStream() {
    }

    @Override
    public int read() {
        return -1;
    }
}