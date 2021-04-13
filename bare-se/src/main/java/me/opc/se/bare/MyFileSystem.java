package me.opc.se.bare;

import org.graalvm.polyglot.io.FileSystem;

import java.io.IOException;
import java.net.URI;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.*;
import java.nio.file.attribute.FileAttribute;
import java.util.Map;
import java.util.Set;

public class MyFileSystem implements FileSystem {

  private String moduleName = "parseBeers";
  private String path =

  private FileSystem defaultFs = FileSystem.newDefaultFileSystem();

  @Override
  public Path parsePath(URI uri) {
    return null;
  }

  @Override
  public Path parsePath(String path) {
    return null;
  }

  @Override
  public void checkAccess(Path path, Set<? extends AccessMode> modes, LinkOption... linkOptions) throws IOException {

  }

  @Override
  public void createDirectory(Path dir, FileAttribute<?>... attrs) throws IOException {

  }

  @Override
  public void delete(Path path) throws IOException {

  }

  @Override
  public SeekableByteChannel newByteChannel(Path path, Set<? extends OpenOption> options, FileAttribute<?>... attrs) throws IOException {
    return null;
  }

  @Override
  public DirectoryStream<Path> newDirectoryStream(Path dir, DirectoryStream.Filter<? super Path> filter) throws IOException {
    return null;
  }

  @Override
  public Path toAbsolutePath(Path path) {
    return null;
  }

  @Override
  public Path toRealPath(Path path, LinkOption... linkOptions) throws IOException {
    return null;
  }

  @Override
  public Map<String, Object> readAttributes(Path path, String attributes, LinkOption... options) throws IOException {
    return null;
  }
}
