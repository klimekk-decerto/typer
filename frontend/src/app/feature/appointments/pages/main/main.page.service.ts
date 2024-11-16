import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";

@Injectable()
export class MainPageService {

  constructor(private http: HttpClient) {
  }

  test() {
    return this.http.get('https://dog.ceo/api/breeds/image/random').subscribe();
  }
  test2() {
    return this.http.get('app/api/test').subscribe();
  }
}
