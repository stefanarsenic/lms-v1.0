import {Component, OnDestroy, Renderer2, ViewChild} from '@angular/core';
import {KorisnikService} from "../../../services/korisnik.service";
import {NavigationEnd, Router, RouterLink, RouterOutlet} from "@angular/router";
import {NgClass, NgIf} from "@angular/common";
import {UsersComponent} from "../users/users.component";
import {Button} from "primeng/button";
import {Subscription} from "rxjs";
import {AppSidebarComponent} from "../../nastavnik/nastavnik/app.sidebar.component";
import {AppTopBarComponent} from "../../nastavnik/nastavnik/app.topbar.component";
import {LayoutService} from "../../nastavnik/nastavnik/service/app.layout.service";
import {filter} from "rxjs/operators";
import {AppFooterComponent} from "../../nastavnik/nastavnik/app.footer.component";

@Component({
  selector: 'app-admin-profile',
  standalone: true,
  imports: [
    NgIf,
    UsersComponent,
    Button,
    RouterOutlet,
    RouterLink,
    AppFooterComponent,
    AppSidebarComponent,
    AppTopBarComponent,
    NgClass
  ],
  templateUrl: './admin-profile.component.html',
  styleUrl: './admin-profile.component.css'
})
export class AdminProfileComponent implements OnDestroy {

  overlayMenuOpenSubscription: Subscription;

  menuOutsideClickListener: any;

  profileMenuOutsideClickListener: any;

@ViewChild(AppSidebarComponent) appSidebar!: AppSidebarComponent;

@ViewChild(AppTopBarComponent) appTopbar!: AppTopBarComponent;

  model1:any=[]

  constructor(public layoutService: LayoutService, public renderer: Renderer2, public router: Router) {
    this.model1 = [
      {
        label: 'Admin Dashboard',
        items: [
          { label: 'Dashboard', icon: 'pi pi-fw pi-home', routerLink: 'admin' },
          { label: 'Korisnici', icon: 'pi pi-fw pi-users', routerLink: 'korisnici' },
          { label: 'Profesori', icon: 'pi pi-fw pi-user', routerLink: 'nastavnici' },
          { label: 'Studenti', icon: 'pi pi-fw pi-users', routerLink: 'studenti' },
          { label: 'Studijski Programi', icon: 'pi pi-fw pi-bookmark', routerLink: 'studijski-programi' },
          { label: 'Organizacija', icon: 'pi pi-fw pi-sitemap', routerLink: 'organizacija-studijskog-programa' },
          { label: 'Šifarnik', icon: 'pi pi-fw pi-list', routerLink: 'uloge' },
          { label: 'Predmeti', icon: 'pi pi-fw pi-book', routerLink: 'predmeti' }
        ]
      }
    ];


    this.overlayMenuOpenSubscription = this.layoutService.overlayOpen$.subscribe(() => {
      if (!this.menuOutsideClickListener) {
        this.menuOutsideClickListener = this.renderer.listen('document', 'click', event => {
          const isOutsideClicked = !(this.appSidebar.el.nativeElement.isSameNode(event.target) || this.appSidebar.el.nativeElement.contains(event.target)
            || this.appTopbar.menuButton.nativeElement.isSameNode(event.target) || this.appTopbar.menuButton.nativeElement.contains(event.target));

          if (isOutsideClicked) {
            this.hideMenu();
          }
        });
      }

      if (!this.profileMenuOutsideClickListener) {
        this.profileMenuOutsideClickListener = this.renderer.listen('document', 'click', event => {
          const isOutsideClicked = !(this.appTopbar.menu.nativeElement.isSameNode(event.target) || this.appTopbar.menu.nativeElement.contains(event.target)
            || this.appTopbar.topbarMenuButton.nativeElement.isSameNode(event.target) || this.appTopbar.topbarMenuButton.nativeElement.contains(event.target));

          if (isOutsideClicked) {
            this.hideProfileMenu();
          }
        });
      }

      if (this.layoutService.state.staticMenuMobileActive) {
        this.blockBodyScroll();
      }
    });

    this.router.events.pipe(filter(event => event instanceof NavigationEnd))
      .subscribe(() => {
        this.hideMenu();
        this.hideProfileMenu();
      });


  }

  hideMenu() {
    this.layoutService.state.overlayMenuActive = false;
    this.layoutService.state.staticMenuMobileActive = false;
    this.layoutService.state.menuHoverActive = false;
    if (this.menuOutsideClickListener) {
      this.menuOutsideClickListener();
      this.menuOutsideClickListener = null;
    }
    this.unblockBodyScroll();
  }

  hideProfileMenu() {
    this.layoutService.state.profileSidebarVisible = false;
    if (this.profileMenuOutsideClickListener) {
      this.profileMenuOutsideClickListener();
      this.profileMenuOutsideClickListener = null;
    }
  }

  blockBodyScroll(): void {
    if (document.body.classList) {
    document.body.classList.add('blocked-scroll');
  }
else {
    document.body.className += ' blocked-scroll';
  }
}

  unblockBodyScroll(): void {
    if (document.body.classList) {
    document.body.classList.remove('blocked-scroll');
  }
else {
    document.body.className = document.body.className.replace(new RegExp('(^|\\b)' +
      'blocked-scroll'.split(' ').join('|') + '(\\b|$)', 'gi'), ' ');
  }
}

  get containerClass() {
    return {
      'layout-theme-light': this.layoutService.config().colorScheme === 'light',
      'layout-theme-dark': this.layoutService.config().colorScheme === 'dark',
      'layout-overlay': this.layoutService.config().menuMode === 'overlay',
      'layout-static': this.layoutService.config().menuMode === 'static',
      'layout-static-inactive': this.layoutService.state.staticMenuDesktopInactive && this.layoutService.config().menuMode === 'static',
      'layout-overlay-active': this.layoutService.state.overlayMenuActive,
      'layout-mobile-active': this.layoutService.state.staticMenuMobileActive,
      'p-input-filled': this.layoutService.config().inputStyle === 'filled',
      'p-ripple-disabled': !this.layoutService.config().ripple
    }
  }

  ngOnDestroy() {
    if (this.overlayMenuOpenSubscription) {
      this.overlayMenuOpenSubscription.unsubscribe();
    }

    if (this.menuOutsideClickListener) {
      this.menuOutsideClickListener();
    }
  }

}
