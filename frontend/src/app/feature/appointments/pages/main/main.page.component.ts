import {Component, OnInit} from "@angular/core";
import {MatSlideToggle} from "@angular/material/slide-toggle";
import {MainPageService} from "./main.page.service";

@Component({
  standalone: true,
  selector: 'main-page',
  imports: [
    MatSlideToggle
  ],
  providers: [
    MainPageService
  ],
  templateUrl: './main.page.component.html'
})
export class MainPageComponent implements OnInit {

  constructor(private mainPageService: MainPageService) {
  }

  ngOnInit(): void {
  }

  toogle() {
    console.log("sssss")
    this.mainPageService.test()
    this.mainPageService.test2()
  }
}
