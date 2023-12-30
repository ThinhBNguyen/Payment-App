import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { RemoteService } from '../remote.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, RouterLink, CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})

export class LoginComponent {
  userName: string = '';
  passWord: string = '';

  loginMessage: string = '';
  isValidMessage: string = '';

  constructor(private remoteService : RemoteService) {}

  login():void{
     this.remoteService.login(this.userName, this.passWord).subscribe(
       data=>{
        this.loginMessage = 'Login successful!';
    },
    error =>{
      this.isValidMessage = 'Invalid username or password.';
    })
  }

}
