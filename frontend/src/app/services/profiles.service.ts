import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {catchError, Observable, of, tap} from "rxjs";
import {Profile} from "../models/profile";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class ProfilesService {

  private swimmersUrl = "/profiles/swim"

  constructor(private http: HttpClient) { }

  getSwimmers(): Observable<Profile[]> {
    return this.http.get<Profile[]>(`${environment.API_URL}${this.swimmersUrl}`)
      .pipe(
        tap(_ => console.log('swim profiles fetched')),
        catchError(this.handleError<Profile[]>('getSwimmers', []))
      )
  }


  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      console.error(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
}
