export interface CompetitionModel {
    id: number,
    name: string;
    matches: MatchesModel[];
}

export interface MatchesModel {
  matchId: number;
  firstTeamId: number;
  secondTeamId: number;
  firstTeamScore: number;
  secondTeamScore: number;
  date: string;
}
