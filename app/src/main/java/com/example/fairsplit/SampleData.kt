package com.example.fairsplit

import com.example.fairsplit.DataModels.GroupModel
import com.example.fairsplit.DataModels.ExpenseModel
import com.example.fairsplit.DataModels.SplitExpenseModel

/**
 * Data contoh (mock) untuk pengembangan dan pengujian UI.
 *
 * Variabel `sampleGroups` berisi daftar grup contoh dengan member dan
 * riwayat pengeluaran sehingga layar dapat menampilkan informasi tanpa
 * perlu sumber data eksternal.
 */
val sampleGroups = listOf(
    GroupModel(
        id = 1,
        name = "Trip to Istanbul",
        memberList = listOf("Ali", "Omar", "Soran"),
        expenseList = listOf(
            ExpenseModel(1, "Flights", 200.0, "Ali",
                listOf(
                    SplitExpenseModel("Ali", 66.67),
                    SplitExpenseModel("Omar", 66.67),
                    SplitExpenseModel("Soran", 66.67)
                ),
                1725100800000, "Travel"), // 1 July 2024
            ExpenseModel(2, "Hotel", 250.0, "Omar",
                listOf(
                    SplitExpenseModel("Omar", 100.0),
                    SplitExpenseModel("Ali", 75.0),
                    SplitExpenseModel("Soran", 75.0)
                ),
                1725187200000, "Accommodation"), // 2 July 2024
            ExpenseModel(3, "Museum tickets", 117.0, "Ali",
                listOf(
                    SplitExpenseModel("Ali", 39.0),
                    SplitExpenseModel("Omar", 39.0),
                    SplitExpenseModel("Soran", 39.0)
                ),
                1725273600000, "Activities") // 3 July 2024
        ),
        image = "https://cdn-imgix.headout.com/media/images/0bc7bfc5d039409e94b0fc256ca3008d-25579-istanbul-combo-topkapi-palace--hagia-sophia---blue-mosque-02.jpg?auto=format&w=702.4499999999999&h=401.4&q=90&fit=crop&ar=7%3A4&crop=faces",
        date = 1725100800000,
        totalExpense = 567.0
    ),
    GroupModel(
        id = 2,
        name = "Dinner at Nandos",
        memberList = listOf("Adam", "Sami", "Soran"),
        expenseList = listOf(
            ExpenseModel(4, "Dinner bill", 46.0, "Sami",
                listOf(
                    SplitExpenseModel("Sami", 20.0),
                    SplitExpenseModel("Adam", 14.0),
                    SplitExpenseModel("Soran", 12.0)
                ),
                1723420800000, "Food"), // 12 August 2024
            ExpenseModel(5, "Dessert bill", 22.0, "Adam",
                listOf(
                    SplitExpenseModel("Adam", 10.0),
                    SplitExpenseModel("Sami", 7.0),
                    SplitExpenseModel("Soran", 5.0)
                ),
                1723420800000, "Food") // Same day
        ),
        image = "https://www.nandos.com/wp-content/uploads/2022/04/our-food.jpg",
        date = 1723420800000,
        totalExpense = 68.0
    ),
    GroupModel(
        id = 3,
        name = "Birthday Party",
        memberList = listOf("Sami", "Bilal", "Soran"),
        expenseList = listOf(
            ExpenseModel(6, "Cake", 30.0, "Sami",
                listOf(
                    SplitExpenseModel("Sami", 10.0),
                    SplitExpenseModel("Bilal", 10.0),
                    SplitExpenseModel("Soran", 10.0)
                ),
                1709596800000, "Food"), // 5 March 2024
            ExpenseModel(7, "Drinks", 50.0, "Bilal",
                listOf(
                    SplitExpenseModel("Bilal", 20.0),
                    SplitExpenseModel("Sami", 15.0),
                    SplitExpenseModel("Soran", 15.0)
                ),
                1709596800000, "Food"),
            ExpenseModel(8, "Venue", 120.0, "Sami",
                listOf(
                    SplitExpenseModel("Sami", 40.0),
                    SplitExpenseModel("Bilal", 40.0),
                    SplitExpenseModel("Soran", 40.0)
                ),
                1709596800000, "Entertainment")
        ),
        image = "https://images.squarespace-cdn.com/content/v1/5453fe91e4b04d3504f98892/1585948177274-H87AN940VQ0H6DIF7CPQ/Birthday+Party+In+Quarantine",
        date = 1709596800000,
        totalExpense = 200.0
    ),
    GroupModel(
        id = 4,
        name = "Camping Trip",
        memberList = listOf("Ibrahim", "Hassan", "Soran"),
        expenseList = listOf(
            ExpenseModel(9, "Tent rental", 70.0, "Ibrahim",
                listOf(
                    SplitExpenseModel("Ibrahim", 30.0),
                    SplitExpenseModel("Hassan", 20.0),
                    SplitExpenseModel("Soran", 20.0)
                ),
                1718400000000, "Accommodation"), // 15 June 2024
            ExpenseModel(10, "Camping food", 40.0, "Hassan",
                listOf(
                    SplitExpenseModel("Hassan", 15.0),
                    SplitExpenseModel("Ibrahim", 15.0),
                    SplitExpenseModel("Soran", 10.0)
                ),
                1718400000000, "Food"),
            ExpenseModel(11, "Transport", 50.0, "Soran",
                listOf(
                    SplitExpenseModel("Soran", 20.0),
                    SplitExpenseModel("Hassan", 15.0),
                    SplitExpenseModel("Ibrahim", 15.0)
                ),
                1718400000000, "Travel")
        ),
        image = "https://media.cntraveler.com/photos/607313c3d1058698d13c31b5/1:1/w_1636,h_1636,c_limit/FamilyCamping-2021-GettyImages-948512452-2.jpg",
        date = 1718400000000,
        totalExpense = 160.0
    ),
    GroupModel(
        id = 5,
        name = "TopGolf",
        memberList = listOf("Adam", "Ibrahim", "Soran"),
        expenseList = listOf(
            ExpenseModel(12, "Golf tickets", 60.0, "Soran",
                listOf(
                    SplitExpenseModel("Soran", 20.0),
                    SplitExpenseModel("Adam", 20.0),
                    SplitExpenseModel("Ibrahim", 20.0)
                ),
                1721433600000, "Entertainment"), // 20 July 2024
            ExpenseModel(13, "Restaurant", 50.0, "Soran",
                listOf(
                    SplitExpenseModel("Soran", 20.0),
                    SplitExpenseModel("Adam", 15.0),
                    SplitExpenseModel("Ibrahim", 15.0)
                ),
                1721433600000, "Food")
        ),
        image = "https://www.exploreminnesota.com/sites/default/files/styles/share_image/public/listing_images/ee96d34d400e42e6670d8fb57275fe4dc391f3df_17.jpg?h=6eb229a4&itok=9qOkn4Gn",
        date = 1721433600000,
        totalExpense = 110.0
    ),
    GroupModel(
        id = 6,
        name = "Weekend in Spain",
        memberList = listOf("Adam", "Khalid", "Soran"),
        expenseList = listOf(
            ExpenseModel(14, "Hotel", 200.0, "Adam",
                listOf(
                    SplitExpenseModel("Adam", 80.0),
                    SplitExpenseModel("Khalid", 60.0),
                    SplitExpenseModel("Soran", 60.0)
                ),
                1724976000000, "Accommodation"), // 30 August 2024
            ExpenseModel(15, "Meals", 75.0, "Khalid",
                listOf(
                    SplitExpenseModel("Khalid", 30.0),
                    SplitExpenseModel("Adam", 25.0),
                    SplitExpenseModel("Soran", 20.0)
                ),
                1724976000000, "Food"),
            ExpenseModel(16, "Transportation", 50.0, "Khalid",
                listOf(
                    SplitExpenseModel("Soran", 20.0),
                    SplitExpenseModel("Khalid", 15.0),
                    SplitExpenseModel("Adam", 15.0)
                ),
                1724976000000, "Travel"),
            ExpenseModel(17, "Museum Entry", 45.0, "Khalid",
                listOf(
                    SplitExpenseModel("Adam", 10.0),
                    SplitExpenseModel("Khalid", 10.0),
                    SplitExpenseModel("Soran", 25.0)
                ),
                1725062400000, "Activity"), // 31 August
            ExpenseModel(18, "Tapas Dinner", 90.0, "Adam",
                listOf(
                    SplitExpenseModel("Adam", 20.0),
                    SplitExpenseModel("Khalid", 20.0),
                    SplitExpenseModel("Soran", 50.0)
                ),
                1725062400000, "Food"),
            ExpenseModel(19, "Flamenco Show", 60.0, "Adam",
                listOf(
                    SplitExpenseModel("Adam", 20.0),
                    SplitExpenseModel("Khalid", 20.0),
                    SplitExpenseModel("Soran", 20.0)
                ),
                1725062400000, "Entertainment"),
            ExpenseModel(20, "Airport Taxi", 36.0, "Khalid",
                listOf(
                    SplitExpenseModel("Adam", 12.0),
                    SplitExpenseModel("Khalid", 12.0),
                    SplitExpenseModel("Soran", 12.0)
                ),
                1725148800000, "Travel"), // 1 Sept
            ExpenseModel(21, "Breakfast Groceries", 30.0, "Adam",
                listOf(
                    SplitExpenseModel("Adam", 10.0),
                    SplitExpenseModel("Khalid", 10.0),
                    SplitExpenseModel("Soran", 10.0)
                ),
                1725148800000, "Food")
        ),
        image = "https://i.natgeofe.com/k/e800ca90-2b5b-4dad-b4d7-b67a48c96c91/spain-madrid_16x9.jpg?w=1200",
        date = 1724976000000,
        totalExpense = 616.0
    )
)
