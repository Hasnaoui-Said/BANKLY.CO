import 'dart:convert';
import 'package:bankly_mobile_app/src/util/Jeton.dart';
import 'package:bankly_mobile_app/src/widget/Transaction.page.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

class HomeItemPage extends StatefulWidget {
  const HomeItemPage({super.key});

  @override
  State<HomeItemPage> createState() => _HomeItemPageState();
}

class _HomeItemPageState extends State<HomeItemPage> {
  dynamic wallet;
  dynamic transactions;
  List<dynamic> transactionsItems = [];
  final routers = [
    "Home",
    "User",
    "Wallet",
    "Setting",
  ];
  int currentPage = 0;
  int totalPage = 0;
  int sizePage = 5;
  ScrollController scrollController = ScrollController();

  @override
  void initState() {
    _fetchWallet();
    scrollController.addListener(() {
      if (scrollController.position.pixels ==
          scrollController.position.maxScrollExtent) {
        setState(() {
          if (currentPage < totalPage) {
            currentPage++;
            _fetchTransaction(wallet['uuid']);
          }
        });
      }
    });
  }

  Future<void> _fetchWallet() async {
    String url = "${JetonUtil.url}users/wallet/holder/";
    try {
      String? jeton = await JetonUtil.getToken();
      print(jeton);
      var response = await http
          .get(Uri.parse(url), headers: {"Authorization": "Bearer ${jeton}"});
      if (response.statusCode == 200) {
        setState(() {
          wallet = json.decode(response.body)['data'];
          _fetchTransaction(wallet['uuid']);
        });
      } else if (response.statusCode == 403) {
        print('Failed to load wallet 403');
      } else {
        print("else");
        throw Exception('Failed to load wallet');
      }
    } catch (e) {
      print("catch");
      print(e);
    }
    print("fin fetch");
  }

  Future<void> _fetchTransaction(String walletId) async {
    String url =
        "${JetonUtil.url}operation/wallet/$walletId?page=$currentPage&size=$sizePage";
    try {
      String? jeton = await JetonUtil.getToken();
      var response = await http
          .get(Uri.parse(url), headers: {"Authorization": "Bearer ${jeton}"});
      if (response.statusCode == 200) {
        setState(() {
          print("tayeb");
          print(response.body);
          transactions = json.decode(response.body)['data']['content'];
          transactionsItems.addAll(transactions);
          totalPage = json.decode(response.body)['data']['totalPages'];
        });
      } else {
        throw Exception('Failed to load wallet');
      }
    } catch (e) {
      print(e);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Row(
            children: [
              Expanded(
                child: Container(
                  padding: const EdgeInsets.all(20),
                  decoration: const BoxDecoration(
                      color: Color.fromRGBO(20, 74, 162, 1),
                      borderRadius:
                          BorderRadius.only(bottomRight: Radius.circular(45))),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text(
                        "$currentPage Hello ${totalPage == null ? "" : totalPage}: ${wallet == null ? "" : wallet['holder']}",
                        style: const TextStyle(color: Colors.white),
                      ),
                      const SizedBox(
                        height: 20,
                      ),
                      Text(
                        "\$ : ${wallet == null ? "" : wallet['balance']}",
                        style: const TextStyle(color: Colors.white),
                      ),
                      const SizedBox(
                        height: 10,
                      ),
                      const Text(
                        "Your Balance",
                        style: TextStyle(color: Colors.white),
                      ),
                    ],
                  ),
                ),
              ),
            ],
          ),
          Container(
            padding: const EdgeInsets.all(16.0),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.stretch,
              children: [
                Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    Padding(
                      padding: const EdgeInsets.all(8.0),
                      child: ElevatedButton(
                        onPressed: () {
                          Navigator.push(
                              context,
                              MaterialPageRoute(
                                  builder: (context) => TransactionPage(
                                        walletId: "${wallet['uuid']}",
                                        typeOperation: "depot",
                                      )));
                        },
                        child: Padding(
                          padding: const EdgeInsets.all(8.0),
                          child: Column(
                            children: const [
                              Icon(Icons.file_download_outlined),
                              Text('Depot'),
                            ],
                          ),
                        ),
                      ),
                    ),
                    Padding(
                      padding: const EdgeInsets.all(8.0),
                      child: ElevatedButton(
                        onPressed: () {
                          Navigator.push(
                            context,
                            MaterialPageRoute(
                                builder: (context) => TransactionPage(
                                      walletId: "${wallet['uuid']}",
                                      typeOperation: "withdrawingFunds",
                                    )),
                          );
                        },
                        child: Padding(
                          padding: const EdgeInsets.all(8.0),
                          child: Column(
                            children: const [
                              Icon(Icons.file_upload_outlined),
                              Text('with drawing Funds'),
                            ],
                          ),
                        ),
                      ),
                    ),
                    Padding(
                      padding: const EdgeInsets.all(8.0),
                      child: IconButton(
                        onPressed: () {},
                        icon: const Icon(
                          Icons.more_vert,
                        ),
                      ),
                    ),
                  ],
                ),
              ],
            ),
          ),
          Expanded(
              child: ListView.separated(
                  controller: scrollController,
                  itemBuilder: (context, index) {
                    return ListTile(
                      title: Row(
                        children: [
                          Expanded(
                            child: Container(
                              padding: const EdgeInsets.all(20),
                              decoration: BoxDecoration(
                                color: Colors.white,
                                borderRadius:
                                    const BorderRadius.all(Radius.circular(25)),
                                boxShadow: [
                                  BoxShadow(
                                    color: Colors.grey.withOpacity(0.2),
                                    spreadRadius: 5,
                                    blurRadius: 7,
                                    offset: const Offset(0, 3),
                                  ),
                                ],
                              ),
                              child: Row(
                                mainAxisAlignment:
                                    MainAxisAlignment.spaceBetween,
                                children: [
                                  Icon(
                                    transactionsItems[index]['type'] == "depot"
                                        ? Icons.file_download_outlined
                                        : Icons.file_upload_outlined,
                                    color: transactionsItems[index]['type'] ==
                                            "depot"
                                        ? Colors.green
                                        : Colors.red,
                                  ),
                                  Column(
                                    children: [
                                      Text(
                                          "${transactionsItems[index]['type']}"),
                                      Text(
                                          "${transactionsItems[index]['createDate']}"),
                                    ],
                                  ),
                                  Text(
                                      "${transactionsItems[index]['type'] == "depot" ? "+" : "-"}"
                                      "\$ ${transactionsItems[index]['amount']}",
                                      style: TextStyle(
                                        color: transactionsItems[index]
                                                    ['type'] ==
                                                "depot"
                                            ? Colors.green
                                            : Colors.red,
                                      )),
                                ],
                              ),
                            ),
                          ),
                        ],
                      ),
                    );
                  },
                  separatorBuilder: (context, index) => const Divider(
                        height: 10,
                        color: Color.fromRGBO(20, 74, 162, 1),
                      ),
                  itemCount:
                      transactionsItems == null ? 0 : transactionsItems.length))
        ],
      ),
    );
  }
}
