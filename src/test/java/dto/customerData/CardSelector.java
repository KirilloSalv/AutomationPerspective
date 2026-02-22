package dto.customerData;

public class CardSelector {

    public static Card get(CardType type) {

        return switch (type) {

            case OTP -> new Card(
                    "4000000000000002",
                    "Frolik Bolik",
                    "111",
                    "02",
                    "2029"
            );

            case VISA -> new Card(
                    "4440000009900010",
                    "Tralalela Tralala",
                    "123",
                    "05",
                    "2030"
            );

            case MASTERCARD -> new Card(
                    "5123450000000008",
                    "Shpioniro Golubino",
                    "111",
                    "07",
                    "2028"
            );


            case CASCADING -> new Card(
                    "5455031252665454",
                    "Brr Patapim",
                    "111",
                    "02",
                    "2029"
            );

            case MAESTRO -> new Card(
                    "5000000000000000005",
                    "Lirili Larila",
                    "223",
                    "08",
                    "2032"

            );
        };

    }
}
