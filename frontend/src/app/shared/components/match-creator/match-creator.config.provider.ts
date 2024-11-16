import {Subject} from "rxjs";
import {TournamentFull} from "../../../core/model/tournament";
import {Injectable} from "@angular/core";

export interface MatchCreatorConfig {
  selectedTournament: Subject<TournamentFull>;
}

@Injectable({providedIn: 'root'})
export class MatchCreatorConfigProvider {

  private readonly selectedTournament: Subject<TournamentFull>;

  constructor() {
    this.selectedTournament = new Subject<TournamentFull>;
  }

  public getConfig(): MatchCreatorConfig {
    return {
      selectedTournament: this.selectedTournament
    }
  }



}
