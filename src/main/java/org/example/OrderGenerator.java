public class OrderGenerator {
    static String[] colors;

    public static Orders getDefault() {
        return new Orders("Naruto",
                "Uchiha",
                "Konoha, 142 apt.",
                4,
                "89119111111",
                5,
                "2020-06-06",
                "Saske, come back to Konoha",
                colors);
    }
}