export interface CompetitionModel {
    id: number,
    name: string;
    matches: MatchesModel[];
    teams: TeamModel[];
}

export interface MatchesModel {
  matchId: number;
  firstTeamId: number;
  secondTeamId: number;
  firstTeamScore: number;
  secondTeamScore: number;
  date: string;
}

export interface TeamModel {
    name: string;
    id: number
}
