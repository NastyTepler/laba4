package app;

import exceptions.InvalidFigureException;
import exceptions.StorageException;
import geometry2d.Figure;
import geometry3d.Cylinder;
import storage.BinaryFigureStorage;
import storage.FigureStorage;
import storage.TextFigureStorage;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 1
        System.out.print("Enter path to input text file: ");
        Path inputPath = Paths.get(scanner.nextLine());

        System.out.print("Enter path to output binary file: ");
        Path outputPath = Paths.get(scanner.nextLine());

        FigureStorage textStorage = new TextFigureStorage();
        FigureStorage binaryStorage = new BinaryFigureStorage();

        // 2
        List<Figure> figures;
        try {
            System.out.println("\n--- Reading text file ---");
            figures = textStorage.read(inputPath);
            System.out.println("Read figures: " + figures.size());
        } catch (IOException | StorageException | ClassNotFoundException e) {
            System.err.println("Failed to read text file: " + e.getMessage());
            if (e.getCause() != null) {
                System.err.println("Cause: " + e.getCause().getMessage());
            }
            return;
        }

        // 3
        System.out.println("\n--- Figures ---");
        for (Figure f : figures) {
            System.out.printf("%s | area = %.2f | perimeter = %.2f%n",
                    f.toString(), f.area(), f.perimeter());
        }

        // 4
        System.out.println("\n--- Cylinder ---");
        if (figures.isEmpty()) {
            System.out.println("List is empty, cylinder creation skipped.");
        } else {
            Random random = new Random();
            Figure randomBase = figures.get(random.nextInt(figures.size()));
            System.out.println("Random base: " + randomBase);

            System.out.print("Enter cylinder height: ");
            String line = scanner.nextLine();
            try {
                double height = Double.parseDouble(line);
                Cylinder cylinder = new Cylinder(randomBase, height);
                System.out.printf("Cylinder volume: %.2f%n", cylinder.volume());
            } catch (NumberFormatException e) {
                System.err.println("Not a number: " + line);
            } catch (InvalidFigureException e) {
                System.err.println("Invalid cylinder parameter: " + e.getMessage());
            }
        }

        // 5
        System.out.println("\n--- Writing binary file ---");
        try {
            binaryStorage.write(outputPath, figures);
            System.out.println("Successfully written: " + outputPath.toAbsolutePath());
        } catch (IOException | StorageException e) {
            System.err.println("Failed to write binary file: " + e.getMessage());
            return;
        }

        // 6
        System.out.println("\n--- Verifying binary file ---");
        try {
            List<Figure> readBack = binaryStorage.read(outputPath);
            System.out.println("Read back: " + readBack.size() + " figures");
            for (Figure f : readBack) {
                System.out.println(f);
            }
        } catch (IOException | ClassNotFoundException | StorageException e) {
            System.err.println("Failed to read binary file back: " + e.getMessage());
        }
    }
}