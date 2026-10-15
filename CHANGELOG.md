# Changelog

## [1.3.0](https://github.com/Azure/osdu-spi-workflow/compare/v1.2.0...v1.3.0) (2026-10-14)


### ✨ Features

* Airflow 3 integration with per-run engine-ownership routing (AF2 backward compatible) ([47f5bef](https://github.com/Azure/osdu-spi-workflow/commit/47f5beffd47b1eb239b87c2fd49cebf4aff4029c))
* Airflow 3 integration with per-run engine-ownership routing (AF2 backward compatible) ([4664943](https://github.com/Azure/osdu-spi-workflow/commit/4664943f1ddebefda2447c1c78140bc9d4f6e44f))
* Initial code ([735c606](https://github.com/Azure/osdu-spi-workflow/commit/735c606cb13399ef9a8114ca14ec40875cb00675))


### 🐛 Bug Fixes

* '500' server error is returned for 'Create a new workflow using standard operators of orchestrator' request ([cf1a4e7](https://github.com/Azure/osdu-spi-workflow/commit/cf1a4e7dfcf2085a47568f829701fa41221ae7dd))
* **#475:** Encode XCom keys so BasicAuth Airflow clients accept reserved characters ([befb293](https://github.com/Azure/osdu-spi-workflow/commit/befb293c5897b49d53108415174cdb2f0b715391))
* **#475:** Encode XCom keys so BasicAuth Airflow clients accept reserved characters ([6848cb6](https://github.com/Azure/osdu-spi-workflow/commit/6848cb63e1f2dfff88003bde7596b0dd8f10db5e))
* **#475:** Encode XCom keys so BasicAuth Airflow clients accept reserved characters ([f5964db](https://github.com/Azure/osdu-spi-workflow/commit/f5964db04d6e030282a11694a495b1386d40b686))
* Adding commons-io ([745c22c](https://github.com/Azure/osdu-spi-workflow/commit/745c22c443a7389b838062394cd27da37dfe1b33))
* Adding commons-io ([f2cd79f](https://github.com/Azure/osdu-spi-workflow/commit/f2cd79fd250e2b7b4c81567165a2086be02cc836))
* Adding NOSONAR since mutable list is required by calling code ([c8dffef](https://github.com/Azure/osdu-spi-workflow/commit/c8dffef00ac948e55dffca1ac8c9677a6042671c))
* Adding NOSONAR since mutable list is required by calling code ([0a7b126](https://github.com/Azure/osdu-spi-workflow/commit/0a7b126a9002f731254ad93bb123bdceb00fae77))
* **azure:** Netty-bom before core-lib-azure (lettuce 7.5.2 NoClassDefFoundError) ([8fcab7b](https://github.com/Azure/osdu-spi-workflow/commit/8fcab7b8fb3bb562193a206c878c0f9b1ee0fbaf))
* **azure:** Netty-bom before core-lib-azure (lettuce 7.5.2 NoClassDefFoundError) ([907ea5a](https://github.com/Azure/osdu-spi-workflow/commit/907ea5ab82c5d9f83f0cbba8a41d1ba02111f0dd))
* **bootstrap:** Use access_token instead of id_token; harden bootstrap script ([0cbc600](https://github.com/Azure/osdu-spi-workflow/commit/0cbc60015199524fa1d5da3e3ca377ef0e6ba17a))
* **bootstrap:** Use access_token instead of id_token; harden bootstrap script ([d8e248e](https://github.com/Azure/osdu-spi-workflow/commit/d8e248e9aff35fbce510c76f0fd808f854757729))
* Code smells ([dc0006f](https://github.com/Azure/osdu-spi-workflow/commit/dc0006ff95c01b4a8c513512853e252fb97726fe))
* Cve and spring boot version bump ([08e5657](https://github.com/Azure/osdu-spi-workflow/commit/08e56576bbacce64288dcadc24d70202cf8c4e99))
* Cve and spring boot version bump ([b99a033](https://github.com/Azure/osdu-spi-workflow/commit/b99a033aa349632077e0a36c56a4506d4c889481))
* **cve:** Pom cleanup + spring-boot 3.5.16 CVE remediation ([b4629f4](https://github.com/Azure/osdu-spi-workflow/commit/b4629f42e39929edd9932a926f6f9765a9b91a5f))
* **cve:** Pom cleanup + spring-boot 3.5.16 CVE remediation ([8fcf4ea](https://github.com/Azure/osdu-spi-workflow/commit/8fcf4eac1d1a22ec08131da74d204f22109fed3d))
* **cve:** Stop pinning Netty BOM to 4.1.118.Final in modules ([3319e31](https://github.com/Azure/osdu-spi-workflow/commit/3319e313876fefeaed781ef893d67bf497ad756d))
* Moving test scripts in the buildspec commands ([279ffbe](https://github.com/Azure/osdu-spi-workflow/commit/279ffbe208d8821fc5ca07d2299168972abb4061))
* Netty smtp CVE ([50135c4](https://github.com/Azure/osdu-spi-workflow/commit/50135c4e98842016ed2a1dee52ac12a290633509))
* Netty smtp CVE ([9da27d9](https://github.com/Azure/osdu-spi-workflow/commit/9da27d960b65f068230f4367f1096c1bb086023e))
* Netty-codec tomcat-core cve ([965be30](https://github.com/Azure/osdu-spi-workflow/commit/965be300520bdb07ebaf462cd4672f654721168b))
* Netty-codec tomcat-core cve ([972e035](https://github.com/Azure/osdu-spi-workflow/commit/972e035a4b725dd180f6447f8d809bfd837e5780))
* Spring cves ([b69f1a8](https://github.com/Azure/osdu-spi-workflow/commit/b69f1a8ff70f1ffd4873cbc5e2dd2f537e3e5e4e))
* Spring cves ([b326712](https://github.com/Azure/osdu-spi-workflow/commit/b326712d97452577e3144733962f45d1cea1921a))
* Spring cves ([7a63826](https://github.com/Azure/osdu-spi-workflow/commit/7a6382692aa7bd3ee7f139bebbfb9c68324243de))
* Spring-core bump ([64abcd7](https://github.com/Azure/osdu-spi-workflow/commit/64abcd7363bee3710cbd1084dae6368bfd420d5a))
* Spring-core bump ([3aae5df](https://github.com/Azure/osdu-spi-workflow/commit/3aae5df78c35acbfaae2531707b1db8d7a7d3fb2))
* Tomcat core CVE ([19874b5](https://github.com/Azure/osdu-spi-workflow/commit/19874b500a8b3030ab800e49a1096d416a2db9a1))
* Tomcat core CVE ([4fcaadd](https://github.com/Azure/osdu-spi-workflow/commit/4fcaaddd9ea4812d622a370f75d5931830a29203))
* Tomcat-core CVE ([4c2c68e](https://github.com/Azure/osdu-spi-workflow/commit/4c2c68e08c4a7dcf571f65513786fa8dc7d40cb0))
* Tomcat-core CVE ([5db1cd1](https://github.com/Azure/osdu-spi-workflow/commit/5db1cd181edea893dcf820ea09fc61419cb815aa))
* Update replicas ([a73a2da](https://github.com/Azure/osdu-spi-workflow/commit/a73a2dad8dff6bc5ed413be99f011b1b6694ea6a))
* Update replicas ([094464d](https://github.com/Azure/osdu-spi-workflow/commit/094464d827c12b3eaed0a34b6f47da7c20d7fb84))
* Use parent.version instead of project.version ([acf6830](https://github.com/Azure/osdu-spi-workflow/commit/acf6830dac139a5448ce8d9209c0274b59f95c04))
* Use parent.version instead of project.version ([97d10b2](https://github.com/Azure/osdu-spi-workflow/commit/97d10b245759c9d8cbafbda514f723f894243b8d))


### 📚 Documentation

* Fill in root README for workflow service ([27382fe](https://github.com/Azure/osdu-spi-workflow/commit/27382fe005ad65560dcf493ce73de9d19d6e4cf8))
* Root README in the standard SPI service format ([9866346](https://github.com/Azure/osdu-spi-workflow/commit/98663462c100f9cdb1ee93f70cf44f4d6477e66f))


### 🔧 Miscellaneous

* Add Apache 2.0 license headers to azure java sources ([f211f4e](https://github.com/Azure/osdu-spi-workflow/commit/f211f4ec517bb53c1187d4c6640592a40b7ef45c))
* Add Apache 2.0 license headers to azure test dags ([13cd4a5](https://github.com/Azure/osdu-spi-workflow/commit/13cd4a538f11bd73ed0203015d1e1a00ef0c6096))
* **ci:** Remove IBM jobs from pipeline ([f1f19cc](https://github.com/Azure/osdu-spi-workflow/commit/f1f19cc9d4e03addd93617e34c0fb9b690880d66))
* **ci:** Remove IBM jobs from pipeline ([636040e](https://github.com/Azure/osdu-spi-workflow/commit/636040e3c89ae581c45a4964996db8be8e484160))
* Complete repository initialization ([66d67ae](https://github.com/Azure/osdu-spi-workflow/commit/66d67aeee5a7d25463c6621e43637bd13faca0dc))
* Copy configuration and workflows from main branch ([8e48c17](https://github.com/Azure/osdu-spi-workflow/commit/8e48c1715b80f8fc2c5f34fc41527529e3a51ade))
* Deleting aws helm chart ([a0d9931](https://github.com/Azure/osdu-spi-workflow/commit/a0d993135b91026a803cccd2cf7a3093ac2bdceb))
* Deleting aws helm chart ([80bac49](https://github.com/Azure/osdu-spi-workflow/commit/80bac49106768d37595859929578afe2d5490dd4))
* Fixing sonar issues ([b6568bc](https://github.com/Azure/osdu-spi-workflow/commit/b6568bcf355ad28ed03087774d7ae819cc468872))
* Fixing sonar issues ([81823c9](https://github.com/Azure/osdu-spi-workflow/commit/81823c90e847595757b2077e7f86222fbd05422f))
* **gc:** Decouple gc provider code (GONRG-15426) ([697bd80](https://github.com/Azure/osdu-spi-workflow/commit/697bd806e19a5e4fdab5463106da0ddcd28dc34f))
* **gc:** Decouple gc provider code (GONRG-15426) ([3c1bba8](https://github.com/Azure/osdu-spi-workflow/commit/3c1bba8a3f1854ae968e22ad401e4581fbe3b653))
* Generate filtered upstream tree ([141846d](https://github.com/Azure/osdu-spi-workflow/commit/141846d9a67a9612cb91b02d86f55a3667b653b4))
* License headers for the release review ([64820bd](https://github.com/Azure/osdu-spi-workflow/commit/64820bd85dde19a8802c4f6fe0629bb8ce375950))
* Remove AWS provider and AWS CI/CD ([4a318a8](https://github.com/Azure/osdu-spi-workflow/commit/4a318a8bcb65316ed87065b72f25c8955386d177))
* Remove AWS provider and AWS CI/CD ([4d1f1c7](https://github.com/Azure/osdu-spi-workflow/commit/4d1f1c7c5748e685d6b911fecc7568d5be331a58))
* Removing helm copy from aws buildspec ([4c169a4](https://github.com/Azure/osdu-spi-workflow/commit/4c169a4f56aae6fb700eedccc72f87d7b0e3c5b0))
* Seed fork-owned azure trees ([9bf187c](https://github.com/Azure/osdu-spi-workflow/commit/9bf187c4fc55f7b0d1971bd67265ef64da5a5223))
* **spi:** Add service descriptor for workflow service ([33512bb](https://github.com/Azure/osdu-spi-workflow/commit/33512bb1543e2c12a25ef2c04fcaa907fd8df284))
* **spi:** Add service descriptor for workflow service ([92d280c](https://github.com/Azure/osdu-spi-workflow/commit/92d280c0ffd4ff1398300a72cace037e3007ba4f))
* Sync template updates ([ac60da2](https://github.com/Azure/osdu-spi-workflow/commit/ac60da2dea572973cd831e2b8b27312abbf37752))
* Sync template updates ([ac0e246](https://github.com/Azure/osdu-spi-workflow/commit/ac0e246395446a77dee77eff6516c5961ff11fc5))
* **template-sync:** Sync template updates 2026-10-13 ([74688ac](https://github.com/Azure/osdu-spi-workflow/commit/74688ac6604f883a83dca259610d87d752972cc6))
* **template-sync:** Sync template updates 2026-10-14 ([62761b5](https://github.com/Azure/osdu-spi-workflow/commit/62761b592322ddee98da937772b93d8dc6569734))


### ♻️ Code Refactoring

* **gc/bootstrap:** Migrate bootstrap deployment to kubernetes job (GONRG-14698) ([9c512a6](https://github.com/Azure/osdu-spi-workflow/commit/9c512a61ead7fa28ebe160159bea1afc511e5231))
* **gc/bootstrap:** Migrate bootstrap deployment to kubernetes job (GONRG-14698) ([8f57237](https://github.com/Azure/osdu-spi-workflow/commit/8f5723729731783ce5f49e6e8b3996d840b9e643))


### 🧪 Tests

* Unique workflow names in integration tests to avoid 409 Conflict on setup (AWS + CIMPL) ([de34050](https://github.com/Azure/osdu-spi-workflow/commit/de3405088eb22f53cf1671dab43e6bafcc1f1e58))
* Unique workflow names in integration tests to avoid 409 Conflict on setup (AWS + CIMPL) ([5549158](https://github.com/Azure/osdu-spi-workflow/commit/55491587deabc4bbf6b673eb80df08afccc6b894))


### 🔨 Build System

* **deps:** Bump jackson, spring-beans, netty, and freemarker ([3d3884c](https://github.com/Azure/osdu-spi-workflow/commit/3d3884cfa78867dc012deb54eb2202f6ef2af378))
* **deps:** Bump jackson, spring-beans, netty, and freemarker ([e5fc26e](https://github.com/Azure/osdu-spi-workflow/commit/e5fc26ec66dbabab2129de40818a0ceabac464d8))


### ⚙️ Continuous Integration

* Add bootstrap image job ([db89c43](https://github.com/Azure/osdu-spi-workflow/commit/db89c431e909a081892122c8e9e0cf5cb9b39f80))
* Add variable for tests ([0ff4a53](https://github.com/Azure/osdu-spi-workflow/commit/0ff4a53a7f1ac62f0e3a6400a110a6ab74842e8b))
* Fix chart name ([5ac8899](https://github.com/Azure/osdu-spi-workflow/commit/5ac88999195ebc6e0cc1995b39884de5135f0d0b))
* Fix chart templates ([2d3f87c](https://github.com/Azure/osdu-spi-workflow/commit/2d3f87cdd9e23b42e667b6e198facc45222b5bb6))
* Temporary disable other providers ([5329c39](https://github.com/Azure/osdu-spi-workflow/commit/5329c39163a742a38ffd1cb1b441eeadd27dab5c))
