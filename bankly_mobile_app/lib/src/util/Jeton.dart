import 'package:shared_preferences/shared_preferences.dart';

class JetonUtil {

  static String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzYWlkaGFzbmFvdWkiLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjkwOTAvbG9naW4iLCJleHAiOjE2NzgxMzg5NDksImF1dGhvcml0aWVzIjpbIlVTRVIiXX0.Dr-kN7LMRHjeKznidtk0tNVyjDYXZpCwxw--dK38ctQ";

  static String url = "http://172.16.11.83:9090/api/v1/";

  static Future<void>  setToken(String jeton) async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    await prefs.setString('successJeton', jeton);
    // await prefs.setString('refreshJeton', jeton);
  }

  static Future<String?> getToken() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    return prefs.getString('successJeton');
  }

}
