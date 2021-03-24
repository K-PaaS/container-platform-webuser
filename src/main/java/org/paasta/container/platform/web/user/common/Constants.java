package org.paasta.container.platform.web.user.common;

/**
 * Constants 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.08.25
 */
public class Constants {

    // COMMON
    public static final String RESULT_STATUS_SUCCESS = "SUCCESS";
    public static final String RESULT_STATUS_FAIL = "FAIL";

    public static final String TARGET_CP_API = "cpApi";
    public static final String TARGET_COMMON_API = "commonApi";

    public static final String NOT_ASSIGNED_ROLE = "NOT_ASSIGNED_ROLE";
    public static final String NAMESPACE_ADMIN = "NAMESPACE_ADMIN";
    public static final String DEFAULT_ADMIN_ROLE = "paas-ta-container-platform-admin-role";

    public static final String API_URL = "/api";
    public static final String CP_BASE_URL = "/container-platform";
    public static final String CP_INIT_URI = "/container-platform/intro/overview";

    public static final String URI_CLUSTER_NODES = "/container-platform/clusters/nodes";
    public static final String URI_CLUSTER_NAMESPACES = "/container-platform/clusters/namespaces";


    public static final String DEFAULT_AUTH = "ROLE_USER";
    public static final String CP_USER_LOGIN_METADATA_REDIS_KEY = "CP_USER_LOGIN_METADATA";
    public static final String CP_LOGIN_SERVICE_BEAN = "loginService";

    // DASHBOARD URI
    public static final String URI_UNAUTHORIZED ="/common/error/unauthorized";
    public static final String URI_LOGOUT ="/logout";
    public static final String URI_LOGIN = "/signUp/login";
    public static final String URI_INTRO_OVERVIEW = "/container-platform/intro/overview";
    public static final String URI_INTRO_ACCESS_INFO = "/container-platform/intro/accessInfo";
    public static final String URI_INTRO_PRIVATE_REGISTRY_INFO = "/container-platform/intro/privateRegistryInfo";

    public static final String URI_WORKLOAD_OVERVIEW = "/container-platform/workloads/overview";
    public static final String URI_WORKLOAD_DEPLOYMENTS = "/container-platform/workloads/deployments";
    public static final String URI_WORKLOAD_PODS = "/container-platform/workloads/pods";
    public static final String URI_WORKLOAD_REPLICA_SETS = "/container-platform/workloads/replicaSets";
    public static final String URI_WORKLOAD_RESOURCE_COUNT = "/clusters/{cluster:.+}/namespaces/{namespace:.+}/overview";

    public static final String URI_SERVICES = "/container-platform/services";

    public static final String URI_STORAGES = "/container-platform/storages";

    public static final String URI_USERS = "/container-platform/users";
    public static final String URI_USERS_CONFIG = "/container-platform/users/config";
    public static final String URI_USERS_INFO = "/container-platform/info";

    public static final String URI_ROLES = "/container-platform/roles";
    public static final String URI_UPDATE_SELECTED_NAMESPACE = "/container-platform/update/selectedNamespace/{namespace:.+}";
    public static final String URI_USERS_VERIFY_PASSWORD = "/container-platform/check/password";
    // API URI
    public static final String URL_API_LOGIN = "/login?isAdmin=false";
    public static final String URI_API_NAME_SPACES_DETAIL = "/clusters/{cluster:.+}/namespaces/{namespace:.+}";
    public static final String URI_API_NAME_SPACES_RESOURCE_QUOTAS = "/clusters/{cluster:.+}/namespaces/{namespace:.+}/resourceQuotas";
    public static final String URI_API_NAME_SPACES_LIMIT_RANGES= "/clusters/{cluster:.+}/namespaces/{namespace:.+}/limitRanges/template";

    public static final String URI_API_NODES_LIST = "/clusters/{cluster:.+}/nodes/{nodeName:.+}";

    public static final String URI_API_DEPLOYMENTS_LIST = "/clusters/{cluster:.+}/namespaces/{namespace:.+}/deployments";
    public static final String URI_API_DEPLOYMENTS_DETAIL = "/clusters/{cluster:.+}/namespaces/{namespace:.+}/deployments/{deploymentName:.+}";
    public static final String URI_API_DEPLOYMENTS_YAML = "/clusters/{cluster:.+}/namespaces/{namespace:.+}/deployments/{deploymentName:.+}/yaml";
    public static final String URI_API_DEPLOYMENTS_CREATE = "/clusters/{cluster:.+}/namespaces/{namespace:.+}/deployments";
    public static final String URI_API_DEPLOYMENTS_UPDATE = "/clusters/{cluster:.+}/namespaces/{namespace:.+}/deployments/{deploymentName:.+}";
    public static final String URI_API_DEPLOYMENTS_DELETE = "/clusters/{cluster:.+}/namespaces/{namespace:.+}/deployments/{deploymentName:.+}";

    public static final String URI_API_PODS_LIST = "/clusters/{cluster:.+}/namespaces/{namespace:.+}/pods";
    public static final String URI_API_PODS_DETAIL = "/clusters/{cluster:.+}/namespaces/{namespace:.+}/pods/{podName:.+}";
    public static final String URI_API_PODS_YAML = "/clusters/{cluster:.+}/namespaces/{namespace:.+}/pods/{podName:.+}/yaml";
    public static final String URI_API_PODS_LIST_BY_SELECTOR = "/clusters/{cluster:.+}/namespaces/{namespace:.+}/pods/resources";
    public static final String URI_API_PODS_LIST_BY_NODE = "/clusters/{cluster:.+}/namespaces/{namespace:.+}/pods/nodes/{nodeName:.+}";
    public static final String URI_API_PODS_LIST_BY_SELECTOR_WITH_SERVICE = "/clusters/{cluster:.+}/namespaces/{namespace:.+}/pods/service/{serviceName:.+}";
    public static final String URI_API_PODS_CREATE = "/clusters/{cluster:.+}/namespaces/{namespace:.+}/pods";
    public static final String URI_API_POD_DELETE = "/clusters/{cluster:.+}/namespaces/{namespace:.+}/pods/{podName:.+}";
    public static final String URI_API_POD_UPDATE = "/clusters/{cluster:.+}/namespaces/{namespace:.+}/pods/{podName:.+}";

    public static final String URI_API_REPLICA_SETS_LIST = "/clusters/{cluster:.+}/namespaces/{namespace:.+}/replicaSets";
    public static final String URI_API_REPLICA_SETS_DETAIL = "/clusters/{cluster:.+}/namespaces/{namespace:.+}/replicaSets/{replicaSetName:.+}";
    public static final String URI_API_REPLICA_SETS_YAML = "/clusters/{cluster:.+}/namespaces/{namespace:.+}/replicaSets/{replicaSetName:.+}/yaml";
    public static final String URI_API_REPLICA_SETS_RESOURCES = "/clusters/{cluster:.+}/namespaces/{namespace:.+}/replicaSets/resources";
    public static final String URI_API_REPLICA_SETS_CREATE = "/clusters/{cluster:.+}/namespaces/{namespace:.+}/replicaSets";
    public static final String URI_API_REPLICA_SETS_UPDATE = "/clusters/{cluster:.+}/namespaces/{namespace:.+}/replicaSets/{replicaSetName:.+}";
    public static final String URI_API_REPLICA_SETS_DELETE = "/clusters/{cluster:.+}/namespaces/{namespace:.+}/replicaSets/{replicaSetName:.+}";

    public static final String URI_API_SERVICES_LIST =   "/clusters/{cluster:.+}/namespaces/{namespace:.+}/services";
    public static final String URI_API_SERVICES_DETAIL = "/clusters/{cluster:.+}/namespaces/{namespace:.+}/services/{serviceName:.+}";
    public static final String URI_API_SERVICES_YAML =   "/clusters/{cluster:.+}/namespaces/{namespace:.+}/services/{serviceName:.+}/yaml";
    public static final String URI_API_SERVICES_UPDATE = "/clusters/{cluster:.+}/namespaces/{namespace:.+}/services/{serviceName:.+}";
    public static final String URI_API_SERVICES_CREATE = "/clusters/{cluster:.+}/namespaces/{namespace:.+}/services";
    public static final String URI_API_SERVICES_DELETE = "/clusters/{cluster:.+}/namespaces/{namespace:.+}/services/{serviceName:.+}";

    public static final String URI_API_ROLES_LIST =   "/clusters/{cluster:.+}/namespaces/{namespace:.+}/roles";
    public static final String URI_API_ROLES_DETAIL = "/clusters/{cluster:.+}/namespaces/{namespace:.+}/roles/{roleName:.+}";
    public static final String URI_API_ROLES_YAML =   "/clusters/{cluster:.+}/namespaces/{namespace:.+}/roles/{roleName:.+}/yaml";

    public static final String URI_API_USERS_LIST = "/clusters/{cluster:.+}/users";
    public static final String URI_API_USERS_LIST_BY_NAMESPACE = "/clusters/{cluster:.+}/namespaces/{namespace:.+}/users";
    public static final String URI_API_USERS_NAMES_LIST_BY_NAMESPACE = "/clusters/{cluster:.+}/namespaces/{namespace:.+}/users/names";
    public static final String URI_API_USERS_DETAIL = "/clusters/{cluster:.+}/namespaces/{namespace:.+}/users/{userId:.+}";
    public static final String URI_API_USERS_INFO = "/clusters/{cluster:.+}/users/{userId:.+}";
    public static final String URI_API_USERS_CONFIG = "/clusters/{cluster:.+}/namespaces/{namespace:.+}/users";



    public static final String URI_API_ENDPOINTS_DETAIL = "/clusters/{cluster:.+}/namespaces/{namespace:.+}/endpoints/{serviceName:.+}";

    public static final String URI_API_EVENTS_LIST = "/clusters/{cluster:.+}/namespaces/{namespace:.+}/events/resources/{resourceUid:.+}";
    public static final String URI_API_NAMESPACE_EVENTS_LIST = "/clusters/{cluster:.+}/namespaces/{namespace:.+}/events";

    public static final String URI_API_SECRETS_DETAIL = "/clusters/{cluster:.+}/namespaces/{namespace:.+}/secrets/{accessTokenName:.+}";

    public static final String URI_API_STORAGES_LIST = "/clusters/{cluster:.+}/namespaces/{namespace:.+}/persistentVolumeClaims";
    public static final String URI_API_STORAGES_DETAIL = "/clusters/{cluster:.+}/namespaces/{namespace:.+}/persistentVolumeClaims/{persistentVolumeClaimName:.+}";
    public static final String URI_API_STORAGES_YAML = "/clusters/{cluster:.+}/namespaces/{namespace:.+}/persistentVolumeClaims/{persistentVolumeClaimName:.+}/yaml";
    public static final String URI_API_STORAGES_UPDATE = "/clusters/{cluster:.+}/namespaces/{namespace:.+}/persistentVolumeClaims/{persistentVolumeClaimName:.+}";
    public static final String URI_API_STORAGES_CREATE = "/clusters/{cluster:.+}/namespaces/{namespace:.+}/persistentVolumeClaims";
    public static final String URI_API_STORAGES_DELETE = "/clusters/{cluster:.+}/namespaces/{namespace:.+}/persistentVolumeClaims/{persistentVolumeClaimName:.+}";

    public static final String URI_API_COMMON_RESOURCE_CREATE_VIEW= "/common/resource/{namespace:.+}/{resourceKind:.+}/create";
    public static final String URI_API_COMMON_RESOURCE_UPDATE_VIEW = "/common/resource/{namespace:.+}/{resourceKind:.+}/{resourceName:.+}/update";

    //COMMON RESOURCE
    public static final String URI_API_COMMON_RESOURCE_YAML = "/clusters/{cluster:.+}/namespaces/{namespace:.+}/{resourceKind:.+}/{resourceName:.+}/yaml";
    public static final String URI_API_COMMON_RESOURCE_CREATE = "/clusters/{cluster:.+}/namespaces/{namespace:.+}/{resourceKind:.+}";
    public static final String URI_API_COMMON_RESOURCE_UPDATE = "/clusters/{cluster:.+}/namespaces/{namespace:.+}/{resourceKind:.+}/{resourceName:.+}";
    public static final String URI_API_COMMON_RESOURCE_DELETE = "/clusters/{cluster:.+}/namespaces/{namespace:.+}/{resourceKind:.+}/{resourceName:.+}";

    //Private Registry
    public static final String URI_API_PRIVATE_REGISTRY_DETAIL = "/privateRegistry/{imageName:.+}";

    public static final Integer OVERVIEW_LIMIT_COUNT = 5;
    public static final Integer DEFAULT_LIMIT_COUNT = 10;

    public static final String CP_REMEMBER_ME_KEY = "cp-user-remember-me";
    public static final String CP_USER_METADATA_KEY ="cp-user-metadata";
    public static final String CP_SELECTED_NAMESPACE_KEY ="cp-user-selected-ns";
    public static final String REMAIN_ITEM_COUNT_KEY = "remainingItemCount";
    public static final String CP_CLUSTER_NAME_KEY ="cp-user-cluster-name";

    public static final String REDIRECT_VIEW = "redirect:";

    public static final String NO_VALUE_INPUT = "입력된 값이 없습니다.";
    public static final String EMPTY_VALUE ="-";

    public static final String CHECK_TRUE = "true";
    public static final String CHECK_FALSE = "false";

    public static final String[] PERMIT_PATH = new String[]{"/", "/login", "/signUp", "/register", "/error", "/common/error/unauthorized", "/logout"};

    private Constants() {
        throw new IllegalStateException();
    }

}
