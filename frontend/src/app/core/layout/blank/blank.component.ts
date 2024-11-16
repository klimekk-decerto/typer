import {Component} from '@angular/core';

import {RouterOutlet} from '@angular/router';
import {MaterialModule} from "../../../material.module";

@Component({
  selector: 'app-blank',
  templateUrl: './blank.component.html',
  styleUrls: [],
  standalone: true,
  imports: [RouterOutlet, MaterialModule],
})
export class BlankComponent {
  constructor() {
  }
}
