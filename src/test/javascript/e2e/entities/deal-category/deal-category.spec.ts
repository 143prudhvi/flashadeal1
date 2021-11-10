import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { DealCategoryComponentsPage, DealCategoryDeleteDialog, DealCategoryUpdatePage } from './deal-category.page-object';

const expect = chai.expect;

describe('DealCategory e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let dealCategoryComponentsPage: DealCategoryComponentsPage;
  let dealCategoryUpdatePage: DealCategoryUpdatePage;
  let dealCategoryDeleteDialog: DealCategoryDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load DealCategories', async () => {
    await navBarPage.goToEntity('deal-category');
    dealCategoryComponentsPage = new DealCategoryComponentsPage();
    await browser.wait(ec.visibilityOf(dealCategoryComponentsPage.title), 5000);
    expect(await dealCategoryComponentsPage.getTitle()).to.eq('Deal Categories');
    await browser.wait(
      ec.or(ec.visibilityOf(dealCategoryComponentsPage.entities), ec.visibilityOf(dealCategoryComponentsPage.noResult)),
      1000
    );
  });

  it('should load create DealCategory page', async () => {
    await dealCategoryComponentsPage.clickOnCreateButton();
    dealCategoryUpdatePage = new DealCategoryUpdatePage();
    expect(await dealCategoryUpdatePage.getPageTitle()).to.eq('Create or edit a Deal Category');
    await dealCategoryUpdatePage.cancel();
  });

  it('should create and save DealCategories', async () => {
    const nbButtonsBeforeCreate = await dealCategoryComponentsPage.countDeleteButtons();

    await dealCategoryComponentsPage.clickOnCreateButton();

    await promise.all([
      dealCategoryUpdatePage.setNameInput('name'),
      dealCategoryUpdatePage.setIconInput('icon'),
      dealCategoryUpdatePage.setDescriptionInput('description'),
    ]);

    await dealCategoryUpdatePage.save();
    expect(await dealCategoryUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await dealCategoryComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last DealCategory', async () => {
    const nbButtonsBeforeDelete = await dealCategoryComponentsPage.countDeleteButtons();
    await dealCategoryComponentsPage.clickOnLastDeleteButton();

    dealCategoryDeleteDialog = new DealCategoryDeleteDialog();
    expect(await dealCategoryDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Deal Category?');
    await dealCategoryDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(dealCategoryComponentsPage.title), 5000);

    expect(await dealCategoryComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
