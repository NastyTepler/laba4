package storage;

import exceptions.InvalidFigureException;
import exceptions.StorageException;
import geometry2d.Circle;
import geometry2d.Figure;
import geometry2d.Rectangle;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TextFigureStorage implements FigureStorage {

    @Override
    public List<Figure> read(Path path) throws IOException, StorageException {
        List<Figure> figures = new ArrayList<>();
        if (!Files.exists(path)) {
            return figures;
        }

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                if (line.isEmpty()) continue;

                try {
                    figures.add(parseLine(line));
                } catch (InvalidFigureException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    // вариант Б: пропускаем плохую строку и сообщаем
                    System.err.println("Line " + lineNumber + ": skipped (" + e.getMessage() + ")");
                }
            }
        }
        return figures;
    }

    // Разбирает одну строку в фигуру
    private Figure parseLine(String line) {
        String[] parts = line.split(";");
        String type = parts[0].trim().toLowerCase();

        if (type.equals("circle")) {
            if (parts.length != 2) {
                throw new InvalidFigureException("Circle needs 1 parameter, got " + (parts.length - 1));
            }
            double radius = Double.parseDouble(parts[1]);
            return new Circle(radius);
        } else if (type.equals("rectangle")) {
            if (parts.length != 3) {
                throw new InvalidFigureException("Rectangle needs 2 parameters, got " + (parts.length - 1));
            }
            double width = Double.parseDouble(parts[1]);
            double height = Double.parseDouble(parts[2]);
            return new Rectangle(width, height);
        } else {
            throw new InvalidFigureException("Unknown figure type: " + type);
        }
    }

    @Override
    public void write(Path path, List<Figure> figures) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            for (int i = 0; i < figures.size(); i++) {
                Figure f = figures.get(i);
                if (f instanceof Circle c) {
                    writer.write("circle;" + c.getRadius());
                } else if (f instanceof Rectangle r) {
                    writer.write("rectangle;" + r.getWidth() + ";" + r.getHeight());
                } else {
                    throw new IOException("Unsupported figure type for text write: "
                            + f.getClass().getSimpleName());
                }
                writer.newLine();
            }
        }
    }
}