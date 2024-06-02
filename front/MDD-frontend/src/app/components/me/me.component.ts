import { Component, OnInit } from '@angular/core';
import { User } from '../../interfaces/user.interface';
import { AuthService } from '../../features/auth/services/auth.service';

@Component({
  selector: 'app-me',
  templateUrl: './me.component.html',
  styleUrls: ['./me.component.scss']
})
export class MeComponent implements OnInit {

  public user: User | undefined;

  constructor(private authService: AuthService) { }

  public ngOnInit(): void {
    this.authService.me().subscribe(
      (user: User) => this.user = user
    )
  }

  public back() {
    window.history.back();
  }

}