package cn.luowq.utils;

import cn.luowq.api.internal.apachecommons.io.FileUtils;
import cn.luowq.api.internal.apachecommons.io.IOUtils;

import java.io.*;
import java.util.Enumeration;
import java.util.function.Predicate;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @Author: rowan
 * @Date: 2019/2/18 16:33
 * @Description:
 */
public final class ZipUtils {
    private static final String ERROR_CREATING_DIRECTORY = "Error creating directory: ";

    private ZipUtils() {
    }

    public static File unzip(File zip, File toDir) throws IOException {
        return unzip(zip, toDir, (ZipEntryFilter) (ze) -> {
            return true;
        });
    }

    public static File unzip(InputStream zip, File toDir) throws IOException {
        return unzip(zip, toDir, (ZipEntryFilter) (ze) -> {
            return true;
        });
    }

    /**
     * @deprecated
     */
    @Deprecated
    public static File unzip(InputStream stream, File toDir, ZipUtils.ZipEntryFilter filter) throws IOException {
        return unzip((InputStream) stream, toDir, (Predicate) (new ZipUtils.ZipEntryFilterDelegate(filter)));
    }

    public static File unzip(InputStream stream, File toDir, Predicate<ZipEntry> filter) throws IOException {
        if (!toDir.exists()) {
            FileUtils.forceMkdir(toDir);
        }

        ZipInputStream zipStream = new ZipInputStream(stream);

        File var5;
        try {
            ZipEntry entry;
            while ((entry = zipStream.getNextEntry()) != null) {
                if (filter.test(entry)) {
                    unzipEntry(entry, zipStream, toDir);
                }
            }

            var5 = toDir;
        } finally {
            zipStream.close();
        }

        return var5;
    }

    private static void unzipEntry(ZipEntry entry, ZipInputStream zipStream, File toDir) throws IOException {
        File to = new File(toDir, entry.getName());
        if (entry.isDirectory()) {
            throwExceptionIfDirectoryIsNotCreatable(to);
        } else {
            File parent = to.getParentFile();
            throwExceptionIfDirectoryIsNotCreatable(parent);
            copy(zipStream, to);
        }

    }

    private static void throwExceptionIfDirectoryIsNotCreatable(File to) throws IOException {
        if (!to.exists() && !to.mkdirs()) {
            throw new IOException("Error creating directory: " + to);
        }
    }

    /**
     * @deprecated
     */
    @Deprecated
    public static File unzip(File zip, File toDir, ZipUtils.ZipEntryFilter filter) throws IOException {
        return unzip((File) zip, toDir, (Predicate) (new ZipUtils.ZipEntryFilterDelegate(filter)));
    }

    public static File unzip(File zip, File toDir, Predicate<ZipEntry> filter) throws IOException {
        if (!toDir.exists()) {
            FileUtils.forceMkdir(toDir);
        }

        ZipFile zipFile = new ZipFile(zip);

        try {
            Enumeration entries = zipFile.entries();

            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                if (filter.test(entry)) {
                    File to = new File(toDir, entry.getName());
                    if (entry.isDirectory()) {
                        throwExceptionIfDirectoryIsNotCreatable(to);
                    } else {
                        File parent = to.getParentFile();
                        throwExceptionIfDirectoryIsNotCreatable(parent);
                        copy(zipFile, entry, to);
                    }
                }
            }

            File var11 = toDir;
            return var11;
        } finally {
            zipFile.close();
        }
    }

    private static void copy(ZipInputStream zipStream, File to) throws IOException {
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(to);
            IOUtils.copy(zipStream, fos);
        } finally {
            IOUtils.closeQuietly(fos);
        }

    }

    private static void copy(ZipFile zipFile, ZipEntry entry, File to) throws IOException {
        FileOutputStream fos = new FileOutputStream(to);
        InputStream input = null;

        try {
            input = zipFile.getInputStream(entry);
            IOUtils.copy(input, fos);
        } finally {
            IOUtils.closeQuietly(input);
            IOUtils.closeQuietly(fos);
        }

    }

    public static void zipDir(File dir, File zip) throws IOException {
        OutputStream out = null;
        ZipOutputStream zout = null;

        try {
            out = FileUtils.openOutputStream(zip);
            zout = new ZipOutputStream(out);
            doZipDir(dir, zout);
        } finally {
            IOUtils.closeQuietly(zout);
            IOUtils.closeQuietly(out);
        }

    }

    private static void doZip(String entryName, InputStream in, ZipOutputStream out) throws IOException {
        ZipEntry entry = new ZipEntry(entryName);
        out.putNextEntry(entry);
        IOUtils.copy(in, out);
        out.closeEntry();
    }

    private static void doZip(String entryName, File file, ZipOutputStream out) throws IOException {
        if (file.isDirectory()) {
            entryName = entryName + "/";
            ZipEntry entry = new ZipEntry(entryName);
            out.putNextEntry(entry);
            out.closeEntry();
            File[] files = file.listFiles();
            if (files == null) {
                throw new IllegalStateException("Fail to list files of directory " + file.getAbsolutePath());
            }

            File[] var5 = files;
            int var6 = files.length;

            for (int var7 = 0; var7 < var6; ++var7) {
                File f = var5[var7];
                doZip(entryName + f.getName(), f, out);
            }
        } else {
            InputStream in = new BufferedInputStream(new FileInputStream(file));
            Throwable var19 = null;

            try {
                doZip(entryName, (InputStream) in, out);
            } catch (Throwable var16) {
                var19 = var16;
                throw var16;
            } finally {
                if (in != null) {
                    if (var19 != null) {
                        try {
                            in.close();
                        } catch (Throwable var15) {
                            var19.addSuppressed(var15);
                        }
                    } else {
                        in.close();
                    }
                }

            }
        }

    }

    private static void doZipDir(File dir, ZipOutputStream out) throws IOException {
        File[] children = dir.listFiles();
        if (children == null) {
            throw new IllegalStateException("Fail to list files of directory " + dir.getAbsolutePath());
        } else {
            File[] var3 = children;
            int var4 = children.length;

            for (int var5 = 0; var5 < var4; ++var5) {
                File child = var3[var5];
                doZip(child.getName(), child, out);
            }

        }
    }

    private static class ZipEntryFilterDelegate implements Predicate<ZipEntry> {
        private final ZipUtils.ZipEntryFilter delegate;

        private ZipEntryFilterDelegate(ZipUtils.ZipEntryFilter delegate) {
            this.delegate = delegate;
        }

        @Override
        public boolean test(ZipEntry zipEntry) {
            return this.delegate.accept(zipEntry);
        }
    }

    /**
     * @deprecated
     */
    @Deprecated
    @FunctionalInterface
    public interface ZipEntryFilter {
        boolean accept(ZipEntry var1);
    }
}