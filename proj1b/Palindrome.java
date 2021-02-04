public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> result = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i += 1) {
            result.addLast(word.charAt(i));
        }
        return result;
    }
    public boolean isPalindrome(String word) {
        Deque<Character> wordList = wordToDeque(word);
        return isPalindromeHelper(wordList);
    }
    private boolean isPalindromeHelper(Deque<Character> wordList) {
        if (wordList.size() == 1 || wordList.size() == 0) {
            return true;
        } else if (wordList.removeFirst() == wordList.removeLast()) {
            return isPalindromeHelper(wordList);
        } else {
            return false;
        }
    }
    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> wordList = wordToDeque(word);
        return isPalindromeOffByOneHelper(wordList, cc);
    }
    private boolean isPalindromeOffByOneHelper(Deque<Character> wordList, CharacterComparator cc) {
        if (wordList.size() == 1 || wordList.size() == 0) {
            return true;
        } else if (cc.equalChars(wordList.removeFirst(), wordList.removeLast())) {
            return isPalindromeOffByOneHelper(wordList, cc);
        } else {
            return false;
        }
    }
}
