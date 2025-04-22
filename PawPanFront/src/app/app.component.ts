import { ChangeDetectorRef, Component, Inject, inject, OnDestroy, OnInit } from '@angular/core';
import { RouterOutlet, RouterLink } from '@angular/router';
import { HeaderComponent } from './components/header/header.component';
import {MatListModule} from '@angular/material/list';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatToolbarModule} from '@angular/material/toolbar';
import { MediaMatcher } from '@angular/cdk/layout';
import { MenuItems } from './model/MenuItems';
import { menuItems } from './model/data/data-MenuItems';
import { HttpClientModule } from '@angular/common/http';
import { AuthService } from './services/auth.service';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet,RouterLink ,MatToolbarModule, MatButtonModule, MatIconModule, MatSidenavModule, MatListModule, HeaderComponent, HttpClientModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnDestroy, OnInit{
  static app: AppComponent;
  mobileQuery: MediaQueryList;
  
  fillerNav = Array.from({length: 50}, (_, i) => `Nav Item ${i + 1}`);
  
  menuItems: MenuItems[] = menuItems;
  role: string;
  tieneSesion: boolean;
  

  fillerContent = Array.from(
    {length: 10},
    () =>
      `Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut
       labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco
       laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in
       voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat
       cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.`,
  );

  private _mobileQueryListener: () => void;

  constructor(
    private authService: AuthService,
    public dialog: MatDialog
  ) {
    const changeDetectorRef = inject(ChangeDetectorRef);
    const media = inject(MediaMatcher);

    this.mobileQuery = media.matchMedia('(max-width: 600px)');
    this._mobileQueryListener = () => changeDetectorRef.detectChanges();
    this.mobileQuery.addListener(this._mobileQueryListener);
  }

  ngOnDestroy(): void {
    this.mobileQuery.removeListener(this._mobileQueryListener);
  }

  ngOnInit(): void {
    AppComponent.app = this;
    this.authService.usuario$.subscribe(usuario => {
      this.tieneSesion = usuario? true : false;
      this.role = usuario?.rol;
    });
  }

  esRol(item: MenuItems): boolean{
    return item.role.includes(this.role);
  }

  public static onAlerta(data: any){
    const dejarSeguirRef = AppComponent.app.dialog.open(ModalDialog, {
      minWidth: data.minWidth,
      minHeight: data.minHeight,
      width: 'auto',
      height: 'auto',
      data: data,
      panelClass: 'dialog-container'
    });
    dejarSeguirRef.afterClosed().subscribe(result => {
      //this.ngOnInit();
    });
    return dejarSeguirRef;
	}
  
  shouldRun = true;
}

@Component({
	selector: 'modal',
  standalone: true,
  imports: [
    MatProgressSpinnerModule,
    MatIconModule,
    MatDialogModule,
    MatButtonModule
  ],
	templateUrl: './confirmacion.html',
	styleUrls: ['../styles.scss']
})
export class ModalDialog { 
	
	constructor(
	    public dialogRef: MatDialogRef<ModalDialog>,
	    	@Inject(MAT_DIALOG_DATA) public data: any) {
	}

	onNoClick(): void {
		this.data.onNoClick();
		this.dialogRef.close();
	}
	onAceptClick(){
		this.dialogRef.close();
		this.data.onAceptar();
	}
}
