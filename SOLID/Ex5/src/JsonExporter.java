import java.nio.charset.StandardCharsets;

public class JsonExporter extends Exporter {

    @Override
    public String getFormatName() { return "JSON"; }

    @Override
    public String getContentType() { return "application/json"; }

    @Override
    protected ExportResult generateExport(ExportRequest req) {
        String json = "{\"title\":\"" + escape(req.title) + "\",\"body\":\"" + escape(req.body) + "\"}";
        return new ExportResult(getContentType(), json.getBytes(StandardCharsets.UTF_8));
    }

    private String escape(String s) {
        if (s == null) return "";
        return s.replace("\"", "\\\"");
    }
}