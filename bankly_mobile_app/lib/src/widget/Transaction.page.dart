import 'dart:convert';

import 'package:bankly_mobile_app/src/util/Jeton.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:http/http.dart' as http;

class TransactionPage extends StatefulWidget {
  String walletId;
  String typeOperation;

  TransactionPage({
    super.key,
    required this.walletId,
    required this.typeOperation,
  });

  @override
  State<TransactionPage> createState() => _TransactionPageState();
}

class _TransactionPageState extends State<TransactionPage> {
  TextEditingController amountTextEditingController = TextEditingController();
  final _formKey = GlobalKey<FormState>();
  String? _errorText;

  @override
  void initState() {
    super.initState();
  }

  void _saveOperation(String amount) async {
    var headers = {
      'Authorization': 'Bearer ${JetonUtil.token}',
      'Content-Type': 'application/json'
    };
    var request = http.Request('POST', Uri.parse('${JetonUtil.url}operation/'));
    request.body = json.encode({
      "walletId": widget.walletId,
      "amount": amount,
      "typeOperation": widget.typeOperation
    });
    request.headers.addAll(headers);

    http.StreamedResponse response = await request.send();

    if (response.statusCode == 200) {
        Navigator.pushReplacementNamed(context, '/home');
    } if (response.statusCode == 400) {
      var messageErr = json.decode(await response.stream.bytesToString())["message"];
      setState(() {
        _errorText = 'Error: $messageErr';
      });
    }else {
      setState(() {
        _errorText = 'Error: Failed to save operation';
      });
      print(await response.stream.bytesToString());
    }

  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("BANKLY.CO"),
        backgroundColor: const Color.fromRGBO(20, 74, 162, 1),
        elevation: 0,
      ),
      body: Center(
        child: Form(
          key: _formKey,
          child: Column(
            children: [
              Row(
                children: [
                  Expanded(
                    child: Container(
                      padding: const EdgeInsets.all(20),
                      decoration: const BoxDecoration(
                          color: Color.fromRGBO(20, 74, 162, 1),
                          borderRadius: BorderRadius.only(
                              bottomRight: Radius.circular(45))),
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Center(
                            child: Column(
                              children: [
                                Text(
                                  "${widget.walletId}"
                                  "",
                                  style: const TextStyle(color: Colors.white),
                                ),
                                const SizedBox(
                                  height: 20,
                                ),
                                Text(
                                  widget.typeOperation,
                                  style: const TextStyle(color: Colors.white),
                                ),
                              ],
                            ),
                          ),
                        ],
                      ),
                    ),
                  ),
                ],
              ),
              Container(
                padding: const EdgeInsets.all(10),
                child: TextFormField(
                  controller: amountTextEditingController,
                  keyboardType: TextInputType.number,
                  inputFormatters: [
                    FilteringTextInputFormatter.allow(RegExp(r'^\d+\.?\d{0,2}'))
                  ],
                  validator: (value) {
                    if (value!.isEmpty) {
                      return 'Please enter a value';
                    }
                    final double amount = double.parse(value);
                    if (value!.isEmpty) {
                      return 'Please enter a value';
                    }
                    if (amount < 10.0) {
                      return 'Amount must be at least \$10.00';
                    }
                    if (amount > 2000.0) {
                      return 'Amount must be less than or equal to \$2,000.00';
                    }
                    return _errorText ?? null;
                  },
                  decoration: InputDecoration(
                      // icon: const Icon(Icons.attach_money),
                      prefixIcon: const Icon(Icons.attach_money),
                      contentPadding: const EdgeInsets.all(10),
                      border: OutlineInputBorder(
                          borderSide: const BorderSide(
                            width: 1,
                            color: Color.fromRGBO(20, 74, 162, 1),
                          ),
                          borderRadius: BorderRadius.circular(12))),
                ),
              ),
              Container(
                padding: const EdgeInsets.all(16.0),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.stretch,
                  children: [
                    Row(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        Padding(
                          padding: const EdgeInsets.all(8.0),
                          child: ElevatedButton(
                            onPressed: () {
                              if (_formKey.currentState?.validate() == true) {
                                String amount =
                                    amountTextEditingController.text;
                                _saveOperation(amount);
                              }
                            },
                            child: Padding(
                              padding: const EdgeInsets.all(8.0),
                              child: Row(
                                children: const [
                                  Icon(Icons.file_download_outlined),
                                  SizedBox(width: 10),
                                  Text('Save'),
                                ],
                              ),
                            ),
                          ),
                        ),
                      ],
                    ),
                  ],
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
