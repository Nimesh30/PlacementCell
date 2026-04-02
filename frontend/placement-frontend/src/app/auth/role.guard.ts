import { CanActivateFn, Router } from "@angular/router";
import { inject } from "@angular/core";

export const roleGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);

  const userRole = localStorage.getItem('role');
  const token = localStorage.getItem('token');

  const allowedRoles = route.data?.['roles'] as string[];

  // No token → not logged in → redirect to Home Page
  if (!token || token === 'null' || token === 'undefined') {
    router.navigate(['/']);
    return false;
  }

  if (allowedRoles && allowedRoles.length > 0) {
    if (!userRole || !allowedRoles.includes(userRole)) {
      // If ADMIN trying student route or vice versa, redirect appropriately
      if (userRole === 'ADMIN') {
        router.navigate(['/adminlayout/admindashboard']);
      } else {
        router.navigate(['/layout/userdashboard']);
      }
      return false;
    }
  }

  return true;
};