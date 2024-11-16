import {Component, OnInit} from '@angular/core';
import {MatTab, MatTabGroup} from "@angular/material/tabs";
import {StartComponent} from "../start/start.component";

@Component({
    selector: 'app-main-page',
    standalone: true,
    imports: [
        MatTabGroup,
        MatTab,
        StartComponent
    ],
    styleUrls: ['main-page.component.scss'],
    templateUrl: 'main-page.component.html'
})
export class MainPageComponent implements OnInit {
    constructor() {
    }

    ngOnInit() {
    }

    doNothing() {

    }
}
