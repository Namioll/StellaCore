package me.namioll.stellaCore.Chat;

import me.namioll.stellaCore.StellaCore;

import java.util.HashMap;
import java.util.Map;

public class ChatFilter {

    private final BanwordsManager banwordsManager;

    private static final Map<Character, Character> replacements = new HashMap<>();
    static {
        replacements.put('1', 'и');
        replacements.put('!', 'и');
        replacements.put('0', 'о');
        replacements.put('3', 'з');
        replacements.put('@', 'а');
        replacements.put('4', 'ч');
        replacements.put('a', 'а');
        replacements.put('e', 'е');
        replacements.put('y', 'у');
        replacements.put('o', 'о');
        replacements.put('p', 'п');
        replacements.put('c', 'с');
        replacements.put('x', 'х');
        replacements.put('k', 'к');
        replacements.put('m', 'м');
        replacements.put('h', 'х');
        replacements.put('t', 'т');
        replacements.put('b', 'б');
        replacements.put('g', 'г');
        replacements.put('r', 'р');
        replacements.put('u', 'и');
        replacements.put('i', 'и');
        replacements.put('d', 'д');
        replacements.put('w', 'ш');
        replacements.put('v', 'в');
        replacements.put('s', 'с');
        replacements.put('z', 'з');
        replacements.put('n', 'н');
        replacements.put('f', 'ф');
        replacements.put('l', 'л');
        replacements.put('j', 'ж');
        replacements.put('q', 'к');
    }

    public ChatFilter(StellaCore plugin) {
        this.banwordsManager = plugin.getBanwordsManager();
    }

    private String normalize(String text) {
        text = text.toLowerCase();
        StringBuilder sb = new StringBuilder();
        for (char c : text.toCharArray()) {
            sb.append(replacements.getOrDefault(c, c));
        }
        return sb.toString().replaceAll("[^а-яa-z0-9]", "");
    }

    public String filter(String message) {
        String[] words = message.split(" ");
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            String norm = normalize(word);
            if (banwordsManager.getBanwords().contains(norm)) {
                result.append("**** ");
            } else {
                result.append(word).append(" ");
            }
        }

        return result.toString().trim();
    }
}
