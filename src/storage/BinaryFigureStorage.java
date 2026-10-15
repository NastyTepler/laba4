package storage;

import geometry2d.Figure;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class BinaryFigureStorage implements FigureStorage {

    @Override
    @SuppressWarnings("unchecked")
    public List<Figure> read(Path path) throws IOException, ClassNotFoundException {
        if (!Files.exists(path)) {
            return new ArrayList<>();
        }

        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(path))) {
            Object obj = in.readObject();

            if (!(obj instanceof List<?> list)) {
                throw new IOException("File does not contain a list");
            }
            for (Object item : list) {
                if (!(item instanceof Figure)) {
                    throw new IOException("List contains a non-figure item");
                }
            }
            return (List<Figure>) list;
        }
    }

    @Override
    public void write(Path path, List<Figure> figures) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(path))) {
            out.writeObject(figures);
        }
    }
}