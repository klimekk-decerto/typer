import {Component, OnInit} from '@angular/core';
import {MatTab, MatTabGroup} from "@angular/material/tabs";
import {CompetitionListComponent} from "../start/competition-list.component";
import {MatAnchor, MatButton, MatIconButton} from "@angular/material/button";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatCard, MatCardContent} from "@angular/material/card";
import {MatCheckbox} from "@angular/material/checkbox";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {Router} from "@angular/router";

@Component({
    selector: 'app-main-page',
    standalone: true,
    imports: [
        MatTabGroup,
        MatTab,
        CompetitionListComponent,
        MatButton,
        FormsModule,
        MatAnchor,
        MatCard,
        MatCardContent,
        MatCheckbox,
        MatFormField,
        MatInput,
        MatLabel,
        ReactiveFormsModule,
        MatIconButton
    ],
    styleUrls: ['main-page.component.scss'],
    templateUrl: 'main-page.component.html'
})
export class MainPageComponent implements OnInit {
    constructor(private router: Router) {
    }

    ngOnInit() {
    }

    openList() {
        this.router.navigate(['/admin-dashboard-competitions']);
    }
}
