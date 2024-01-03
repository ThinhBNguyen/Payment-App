import { CommonModule } from '@angular/common';
import { Component, ElementRef, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { FormsModule} from '@angular/forms';
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
  @ViewChild('closeModal') closeModal!: ElementRef;
  @Output() succcessMessage = new EventEmitter<string>();
  @Output() failureMessage = new EventEmitter<string>();
  @Input() accountId: any;

  card = {
    cardNumber: '',
    cardSecurity: '',
    type: 'DEBIT'
  }

  constructor(private remoteService:RemoteService,
              private dashboard: DashboardComponent) {}

  ngOnInit():void {
  }

  onSubmit() {
    this.remoteService.addCard(this.card, this.accountId).subscribe({
      next:(card)=>{
        this.succcessMessage.emit('Card Added');
        this.dashboard.onCardAdded(card, this.accountId);
        this.closeModal.nativeElement.click();
      },
      error:(error) =>{
        this.failureMessage.emit('Failed to add card');
        this.closeModal.nativeElement.click();
      }
    })
  }
  
}
