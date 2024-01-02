import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RemoteService } from '../../remote.service';
import { DashboardComponent } from '../dashboard.component';

@Component({
  selector: 'app-card-modal',
  standalone: true,
  imports: [FormsModule,CommonModule],
  templateUrl: './card-modal.component.html',
  styleUrl: './card-modal.component.css'
})
export class CardModalComponent {

  @Input() accountId: any;

  card = {
    cardNumber: '',
    cardSecurity: '',
    type: 'DEBIT'
  }

  constructor(private remoteService:RemoteService,
              private dashboard: DashboardComponent) {}

  onSubmit() {
    this.remoteService.addCard(this.card, this.accountId).subscribe({
      next:(card)=>{
        console.log('added success ' + card);
        this.dashboard.onCardAdded(card, this.accountId);
      },
      error:(error) =>{
        console.log('added failed ' + error);
      }
    })
  }
}
