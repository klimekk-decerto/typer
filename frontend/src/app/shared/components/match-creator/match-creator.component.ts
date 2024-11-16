import {Component, Input, OnInit} from '@angular/core';
import {OwlDateTimeModule, OwlNativeDateTimeModule} from "@danielmoncada/angular-datetime-picker";
import {MatFormField, MatLabel, MatOption, MatSelect} from "@angular/material/select";
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatIcon} from "@angular/material/icon";
import {MatFabButton} from "@angular/material/button";
import {MatchRestService} from "../../../core/admin/rest/match.rest.service";
import {Subject, take} from "rxjs";
import {MatCard, MatCardContent} from "@angular/material/card";
import {
  MatCell, MatCellDef,
  MatColumnDef,
  MatHeaderCell,
  MatHeaderCellDef,
  MatHeaderRow,
  MatHeaderRowDef,
  MatRecycleRows,
  MatRow, MatRowDef,
  MatTable, MatTableModule
} from "@angular/material/table";
import {MatchCreatorConfig} from "./match-creator.config.provider";

interface SelectedTeam {
  id: number;
  name: string;
}



export interface MatchElement {
  firstTeamName: string;
  secondTeamName: string;
  date: string
  firstTeamResult: number;
  secondTeamResult: number;
}

@Component({
  selector: 'app-match-creator',
  templateUrl: 'match-creator.component.html',
  styleUrl: 'match-creator.component.scss',
  standalone: true,
  imports: [
    OwlDateTimeModule,
    OwlNativeDateTimeModule,
    MatSelect,
    MatFormField,
    MatLabel,
    MatOption,
    FormsModule,
    ReactiveFormsModule,
    MatFormField,
    MatIcon,
    MatFabButton,
    MatCardContent,
    MatCard,
    MatTable,
    MatColumnDef,
    MatHeaderCell,
    MatCell,
    MatRecycleRows,
    MatHeaderRow,
    MatRow,
    MatHeaderRowDef,
    MatCellDef,
    MatRowDef,
    MatTableModule
  ],
})
export class MatchCreatorComponent implements OnInit {

  //@ts-ignore
  @Input() config: MatchCreatorConfig ;

  constructor(private matchRestService: MatchRestService) {
    this.dataSource= [];
  }

  form = new FormGroup({
    firstTeamId: new FormControl('', [Validators.required]),
    secondTeamId: new FormControl('', [Validators.required]),
    date: new FormControl('', [Validators.required]),
  });

  teams1: SelectedTeam[] = [];
  teams2: SelectedTeam[] = [];
  dataSource: MatchElement[] = [];
  displayedColumns: string[] = ['firstTeamName', 'secondTeamName', 'date', 'firstTeamResult', 'secondTeamResult'];


  ngOnInit(): void {
    this.config.selectedTournament.subscribe(tournament => {
      const convertedToSelectedTeam: SelectedTeam[] = tournament?.teams.map((v) => {
        return {id: v.id, name: v.name}
      }) ?? [];
      this.teams1 = convertedToSelectedTeam;
      this.teams2 = convertedToSelectedTeam;


      //@ts-ignore
      this.dataSource =tournament?.matches?.map(match => {
        return {
          firstTeamName: tournament?.teams?.find(t => t.id === match.firstTeamId)?.name,
          secondTeamName: tournament?.teams?.find(t => t.id === match.secondTeamId)?.name,
          date: match?.date,
          firstTeamResult: null,
          secondTeamResult: null}
      }) ?? [];
    })
    this.refreshTeamLists();
  }

  private refreshTeamLists() {

  }

  addMatch(): void {
    this.matchRestService.addMatch({
      competitionId: 2,
      firstTeamId: this.form.controls.firstTeamId.value,
      secondTeamId: this.form.controls.secondTeamId.value,
      date: this.form.controls.date.value
    }).pipe(take(1))
      .subscribe(val => {
        this.config.selectedTournament.next(val);
      })
  }


}
