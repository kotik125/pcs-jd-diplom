public class PageEntry implements Comparable<PageEntry> {
    private final String pdfName;
    private final int page;
    private final int count;

    public PageEntry(String pdfName, int page, int count) {
        this.pdfName = pdfName;
        this.page = page;
        this.count = count;
    }

    @Override
    public int compareTo(PageEntry o) {
        return this.count - o.count;
    }

    @Override
    public String toString() {
        return "PageEntry{pdf=" + this.pdfName + ", page=" + this.page + ", count=" + this.count + "}";
    }
}
