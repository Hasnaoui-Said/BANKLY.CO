
import 'package:bankly_mobile_app/src/util/Jeton.dart';
import 'package:bankly_mobile_app/src/widget/HomeItem.page.dart';
import 'package:bankly_mobile_app/src/widget/Setting.page.dart';
import 'package:bankly_mobile_app/src/widget/User.page.dart';
import 'package:bankly_mobile_app/src/widget/Wallet.page.dart';
import 'package:flutter/material.dart';

class HomePage extends StatefulWidget {
  const HomePage({super.key});

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  int currentPageIndex = 0;
  final tabs = [
    const HomeItemPage(),
    const UserPage(),
    const WalletPage(),
    const SettingPage(),
  ];

  @override
  void initState() {
    super.initState();
    JetonUtil.setToken(JetonUtil.token);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: tabs[currentPageIndex],
      appBar: AppBar(
        title: Text("BANKLY.CO"),
        backgroundColor: const Color.fromRGBO(20, 74, 162, 1),
        elevation: 0,
      ),
      bottomNavigationBar: BottomNavigationBar(
        type: BottomNavigationBarType.fixed,
        selectedFontSize: 20,
        unselectedFontSize: 15,
        selectedItemColor: const Color.fromRGBO(20, 74, 162, 1),
        currentIndex: currentPageIndex,
        items: const [
          BottomNavigationBarItem(
            icon: Icon(Icons.home),
            label: "Home",
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.face),
            label: "User",
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.wallet_travel),
            label: "Wallet",
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.settings),
            label: "Setting",
          ),
        ],
        onTap: (index) {
          setState(() {
            currentPageIndex = index;
          });
        },
      ),
    );
  }
}
