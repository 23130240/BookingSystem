package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SuggestionManager {
    private List<String> suggestions;

    // Constructor
    public SuggestionManager() {
        this.suggestions = new ArrayList<>();
    }

    // Thêm một gợi ý vào danh sách
    public void addSuggestion(String suggestion) {
        suggestions.add(suggestion);
    }

    // Lấy danh sách gợi ý phù hợp với từ khóa
    public List<String> getSuggestions(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return new ArrayList<>();
        }
        String lowerKeyword = keyword.trim().toLowerCase();

        // Lọc danh sách gợi ý dựa trên từ khóa
        return suggestions.stream()
                .filter(s -> s.toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
    }

    // Xóa một gợi ý khỏi danh sách
    public void removeSuggestion(String suggestion) {
        suggestions.remove(suggestion);
    }

    // Xóa toàn bộ danh sách gợi ý
    public void clearSuggestions() {
        suggestions.clear();
    }
}

