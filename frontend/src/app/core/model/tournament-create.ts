export interface TournamentCreate {
  tournamentName: string;
  groupTeams: {[T in keyof string]: string[]}
}
