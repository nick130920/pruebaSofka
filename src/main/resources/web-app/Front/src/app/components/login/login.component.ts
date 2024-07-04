import {Component, inject} from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {catchError, throwError} from "rxjs";
import {HttpErrorResponse} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import {RouterLink} from "@angular/router";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    FormsModule,
    RouterLink,
    NgIf
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  protected authService = inject(AuthService);

  username: string = '';

  constructor() { }

  login() {
    this.authService.login(this.username)
      .pipe(
        catchError((error: HttpErrorResponse) => {
          console.log(error);
          return throwError(() => error);
        }),
      )
      .subscribe((response) => {
        console.log('Login successful', response);
        localStorage.setItem('username', response.username);


      });
  }

  logout(): void {
    const username = localStorage.getItem('username');
    if (username) {
      this.authService.logout(username)
        .pipe(
          catchError((error: HttpErrorResponse) => {
            return throwError(() => error);
          }),
        )
        .subscribe(() => {
          localStorage.removeItem('username');
        });
    } else {
      console.error('No se encontró el username en el localStorage');
    }
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem('username');
  }

}
