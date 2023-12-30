import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { RemoteService, UserDto } from '../remote.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, RouterLink,CommonModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  constructor(private remoteService: RemoteService,
              private router: Router){
  }

  firstName: string = '';
  lastName: string = '';
  userName: string = '';
  passWord: string = '';
  email: string = '';
  role: string = 'USER';

  register(): void{
    let user : UserDto = {
      firstName : this.firstName,
      lastName : this.lastName,
      userName : this.userName,
      passWord : this.passWord,
      email : this.email,
      role : this.role
    }
    this.remoteService.registerUser(user)
    .subscribe(
      (response) => {
        console.log('User registered successfully:', response);
        this.router.navigate(['']);
      },
      (error) => {
        console.error('Error registering user:', error);
      }
    )
  }
}
