import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BooleanSearchEngine implements SearchEngine {

    private Map<String, List<PageEntry>> wordMap = new HashMap<>();

    public BooleanSearchEngine(File pdfsDir) throws IOException {

        File[] pdfsList = pdfsDir.listFiles();

        if (pdfsList == null) {
            throw new IOException("Нет файлов для пойска");
        } else {
            for (File pdfFile : pdfsList) {
                var doc = new PdfDocument(new PdfReader(pdfFile));
                for (int i = 1; i < doc.getNumberOfPages() + 1; i++) {
                    var text = PdfTextExtractor.getTextFromPage(doc.getPage(i));
                    var words = text.split("\\P{IsAlphabetic}+");

                    Map<String, Integer> freqs = new HashMap<>();
                    for (var word : words) {
                        if (word.isEmpty()) {
                            continue;
                        }
                        freqs.put(word.toLowerCase(), freqs.getOrDefault(word, 0) + 1);
                    }

                    for (var word : freqs.keySet()) {
                        if (wordMap.containsKey(word)) {
                            wordMap.get(word).add(new PageEntry(pdfFile.getName(), i, freqs.get(word)));
                        } else {
                            List<PageEntry> list = new ArrayList<>();
                            list.add(new PageEntry(pdfFile.getName(), i, freqs.get(word)));
                            wordMap.put(word, list);
                        }
                    }
                }
            }
        }
        // прочтите тут все pdf и сохраните нужные данные,
        // тк во время поиска сервер не должен уже читать файлы
    }

    @Override
    public List<PageEntry> search(String word) {
        // тут реализуйте поиск по слову
        List<PageEntry> result = this.wordMap.get(word.toLowerCase());
        if (result != null) {
            result.sort(Comparator.reverseOrder());
        }
        return result;
    }
}
