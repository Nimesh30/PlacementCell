import {CanActivateFn,Router } from "@angular/router";
import { inject } from "@angular/core";


export const roleGuard: CanActivateFn = (route, state) => {

  const router = inject(Router);

  const userRole = localStorage.getItem('role');

  const allowedRoles = route.data?.['roles'] as string[];

  if (allowedRoles && allowedRoles.includes(userRole!)) {
    return true;
  }

  // ❌ Not allowed → redirect
  router.navigate(['/login']);
  return false;
};