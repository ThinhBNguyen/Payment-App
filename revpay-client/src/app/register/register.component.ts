import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RemoteService, UserDto } from '../remote.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  constructor(private remoteService: RemoteService){
  }

  firstName: string = "";
  lastName: string = "";
  userName: string = "";
  password: string = "";
  email: string = "";

  register(): void{
    let user : UserDto = {
      firstName : this.firstName,
      lastName : this.lastName,
      userName : this.userName,
      password : this.password,
      email : this.email
    }
    this.remoteService.registerUser(user)
    .subscribe(
      (response) => {
        console.log('User registered successfully:', response);
      },
      (error) => {
        console.error('Error registering user:', error);
      }
    )
  }
}
